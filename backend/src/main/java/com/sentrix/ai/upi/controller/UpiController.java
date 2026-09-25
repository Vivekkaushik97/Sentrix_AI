package com.sentrix.ai.upi.controller;

import com.sentrix.ai.common.ApiResponse;
import com.sentrix.ai.security.event.SecurityEvent;
import com.sentrix.ai.security.event.SecurityEventPublisher;
import com.sentrix.ai.security.event.SecurityEventSource;
import com.sentrix.ai.security.event.SecurityEventType;
import com.sentrix.ai.upi.dto.UpiTransactionDto;
import com.sentrix.ai.upi.entity.UpiTransaction;
import com.sentrix.ai.upi.repository.UpiTransactionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/upi")
public class UpiController {

    private final SecurityEventPublisher publisher;
    private final ObjectMapper objectMapper;
    private final UpiTransactionRepository repository;

    public UpiController(SecurityEventPublisher publisher, ObjectMapper objectMapper, UpiTransactionRepository repository) {
        this.publisher = publisher;
        this.objectMapper = objectMapper;
        this.repository = repository;
    }

    @PostMapping("/ingest")
    public ResponseEntity<ApiResponse<String>> ingestTransaction(@RequestBody UpiTransactionDto transaction) {
        
        SecurityEvent event = new SecurityEvent();
        event.setEventType(SecurityEventType.UPI_TRANSACTION);
        event.setSource(SecurityEventSource.API_GATEWAY);
        event.setEntityReferenceId(transaction.getPayerVpa());
        
        Map<String, Object> payloadMap = objectMapper.convertValue(transaction, new TypeReference<Map<String, Object>>() {});
        event.setPayloadMetadata(payloadMap);
        
        publisher.publish(event);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.success("Transaction accepted for asynchronous processing", "Processing", MDC.get("correlationId")));
    }

    @GetMapping
    public ResponseEntity<Page<UpiTransaction>> getTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        
        Page<UpiTransaction> transactions = repository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp")));
                
        return ResponseEntity.ok(transactions);
    }
}
