package com.sentrix.ai.upi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentrix.ai.common.enums.Severity;
import com.sentrix.ai.security.event.SecurityEvent;
import com.sentrix.ai.security.event.SecurityEventProcessor;
import com.sentrix.ai.security.event.SecurityEventType;
import com.sentrix.ai.upi.dto.UpiTransactionDto;
import com.sentrix.ai.upi.engine.UpiFraudEngine;
import com.sentrix.ai.upi.engine.UpiFraudRule;
import com.sentrix.ai.upi.engine.UpiRiskScorer;
import com.sentrix.ai.upi.entity.UpiTransaction;
import com.sentrix.ai.upi.repository.UpiTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Service
public class UpiEventProcessor implements SecurityEventProcessor {

    private static final Logger logger = LoggerFactory.getLogger(UpiEventProcessor.class);
    
    private final UpiTransactionRepository repository;
    private final UpiFraudEngine fraudEngine;
    private final UpiRiskScorer riskScorer;
    private final ObjectMapper objectMapper;

    public UpiEventProcessor(UpiTransactionRepository repository, UpiFraudEngine fraudEngine, UpiRiskScorer riskScorer, ObjectMapper objectMapper) {
        this.repository = repository;
        this.fraudEngine = fraudEngine;
        this.riskScorer = riskScorer;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(SecurityEventType eventType) {
        return eventType == SecurityEventType.UPI_TRANSACTION;
    }

    @Override
    @Transactional
    public void process(SecurityEvent event) {
        try {
            Map<String, Object> payload = event.getPayloadMetadata();
            UpiTransactionDto dto = objectMapper.convertValue(payload, UpiTransactionDto.class);
            
            UpiTransaction tx = new UpiTransaction();
            tx.setTransactionId(dto.getTransactionId());
            tx.setTimestamp(dto.getTimestamp() != null ? dto.getTimestamp() : OffsetDateTime.now());
            tx.setAmount(dto.getAmount());
            tx.setCurrency(dto.getCurrency() != null ? dto.getCurrency() : "INR");
            tx.setPayerVpa(dto.getPayerVpa());
            tx.setPayeeVpa(dto.getPayeeVpa());
            tx.setDeviceId(dto.getDeviceId());
            tx.setIpAddress(dto.getIpAddress());
            tx.setStatus(dto.getStatus());
            if (dto.getMetadata() != null) {
                tx.setRawMetadata(objectMapper.writeValueAsString(dto.getMetadata()));
            }
            
            // Context for rules (last 24 hours for this VPA)
            List<UpiTransaction> history = repository.findByPayerVpaAndTimestampBetween(
                    tx.getPayerVpa(), 
                    tx.getTimestamp().minusHours(24), 
                    tx.getTimestamp()
            );
            
            List<UpiFraudRule.UpiRuleDetection> detections = fraudEngine.analyze(tx, history);
            int riskScore = riskScorer.calculateRiskScore(tx, detections);
            
            tx.setRiskScore(riskScore);
            
            if (riskScore >= 75) tx.setSeverity(Severity.CRITICAL);
            else if (riskScore >= 50) tx.setSeverity(Severity.HIGH);
            else if (riskScore >= 25) tx.setSeverity(Severity.MEDIUM);
            else tx.setSeverity(Severity.LOW);
            
            repository.save(tx);
            logger.info("Processed UPI transaction {} with risk score {}", tx.getTransactionId(), riskScore);
            
        } catch (IllegalArgumentException | JsonProcessingException e) {
            logger.error("Failed to parse or process UPI Security Event {}", event.getEventId(), e);
        }
    }
}
