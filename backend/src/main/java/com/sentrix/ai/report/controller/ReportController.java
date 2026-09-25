package com.sentrix.ai.report.controller;

import com.sentrix.ai.common.ApiResponse;
import com.sentrix.ai.report.dto.ReportRequestDto;
import com.sentrix.ai.report.dto.ReportResponseDto;
import com.sentrix.ai.report.service.ReportService;
import jakarta.validation.Valid;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReportResponseDto>> generateReport(@Valid @RequestBody ReportRequestDto request) {
        ReportResponseDto response = reportService.generateReport(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Report generated successfully", MDC.get("correlationId")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReportResponseDto>> getReport(@PathVariable UUID id) {
        ReportResponseDto response = reportService.getReport(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Report retrieved successfully", MDC.get("correlationId")));
    }
}
