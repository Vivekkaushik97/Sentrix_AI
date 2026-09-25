package com.sentrix.ai.fraud.service;

import com.sentrix.ai.common.enums.AnalysisStatus;
import com.sentrix.ai.common.enums.AnalysisType;
import com.sentrix.ai.common.enums.Severity;
import com.sentrix.ai.common.exception.ResourceNotFoundException;
import com.sentrix.ai.fraud.dto.FraudAnalysisRequestDto;
import com.sentrix.ai.fraud.dto.FraudAnalysisResponseDto;
import com.sentrix.ai.fraud.engine.FraudEngine;
import com.sentrix.ai.fraud.entity.FraudAnalysis;
import com.sentrix.ai.fraud.repository.FraudAnalysisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FraudAnalysisService {

    private final FraudAnalysisRepository repository;
    private final FraudEngine fraudEngine;

    public FraudAnalysisService(FraudAnalysisRepository repository, FraudEngine fraudEngine) {
        this.repository = repository;
        this.fraudEngine = fraudEngine;
    }

    @Transactional
    public FraudAnalysisResponseDto analyzeTransaction(FraudAnalysisRequestDto request) {
        // Evaluate with deterministic engine
        FraudEngine.FraudEvaluationResult evaluation = fraudEngine.evaluate(request);

        // Determine severity
        int score = evaluation.getRiskScore();
        Severity severity = determineSeverity(score);

        // Build entity
        FraudAnalysis analysis = new FraudAnalysis();
        analysis.setType(AnalysisType.FRAUD);
        analysis.setStatus(AnalysisStatus.COMPLETED);
        analysis.setSeverity(severity);
        
        analysis.setTransactionId(request.getTransactionId());
        analysis.setAmount(request.getAmount());
        analysis.setCurrency(request.getCurrency());
        analysis.setTransactionTimestamp(request.getTransactionTimestamp());
        analysis.setSenderInfo(request.getSenderInfo());
        analysis.setReceiverInfo(request.getReceiverInfo());
        analysis.setDeviceInfo(request.getDeviceInfo());
        analysis.setIpAddress(request.getIpAddress());
        analysis.setLocation(request.getLocation());
        analysis.setPaymentChannel(request.getPaymentChannel());
        
        analysis.setRiskScore(score);
        analysis.setFindings(String.join("||", evaluation.getFindings()));
        analysis.setIsSuspicious(score >= 50);

        // Persist
        FraudAnalysis saved = repository.save(analysis);

        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public FraudAnalysisResponseDto getAnalysis(UUID id) {
        FraudAnalysis analysis = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fraud analysis not found"));
        return mapToDto(analysis);
    }

    private Severity determineSeverity(int score) {
        if (score >= 80) return Severity.CRITICAL;
        if (score >= 50) return Severity.HIGH;
        if (score >= 25) return Severity.MEDIUM;
        if (score > 0) return Severity.LOW;
        return Severity.NONE;
    }

    private FraudAnalysisResponseDto mapToDto(FraudAnalysis entity) {
        FraudAnalysisResponseDto dto = new FraudAnalysisResponseDto();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setSeverity(entity.getSeverity());
        dto.setCreatedAt(entity.getCreatedAt());
        
        dto.setTransactionId(entity.getTransactionId());
        dto.setAmount(entity.getAmount());
        dto.setCurrency(entity.getCurrency());
        dto.setRiskScore(entity.getRiskScore());
        dto.setIsSuspicious(entity.getIsSuspicious());
        
        if (entity.getFindings() != null && !entity.getFindings().isEmpty()) {
            dto.setFindings(Arrays.asList(entity.getFindings().split("\\|\\|")));
        } else {
            dto.setFindings(List.of());
        }
        
        return dto;
    }
}
