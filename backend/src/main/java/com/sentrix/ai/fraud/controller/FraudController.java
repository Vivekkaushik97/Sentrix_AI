package com.sentrix.ai.fraud.controller;

import com.sentrix.ai.common.ApiResponse;
import com.sentrix.ai.fraud.dto.FraudAnalysisRequestDto;
import com.sentrix.ai.fraud.dto.FraudAnalysisResponseDto;
import com.sentrix.ai.fraud.service.FraudAnalysisService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fraud")
public class FraudController {

    private final FraudAnalysisService fraudAnalysisService;

    public FraudController(FraudAnalysisService fraudAnalysisService) {
        this.fraudAnalysisService = fraudAnalysisService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<ApiResponse<FraudAnalysisResponseDto>> analyze(
            @Valid @RequestBody FraudAnalysisRequestDto request) {
        
        FraudAnalysisResponseDto response = fraudAnalysisService.analyzeTransaction(request);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Fraud analysis completed", org.slf4j.MDC.get("correlationId")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FraudAnalysisResponseDto>> getAnalysis(
            @PathVariable UUID id) {
        
        FraudAnalysisResponseDto response = fraudAnalysisService.getAnalysis(id);
        
        return ResponseEntity.ok(ApiResponse.success(response, "Analysis retrieved successfully", org.slf4j.MDC.get("correlationId")));
    }
}
