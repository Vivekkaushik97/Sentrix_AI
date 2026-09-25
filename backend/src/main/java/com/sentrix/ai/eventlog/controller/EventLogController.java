package com.sentrix.ai.eventlog.controller;

import com.sentrix.ai.common.ApiResponse;
import com.sentrix.ai.eventlog.dto.EventLogAnalysisResponseDto;
import com.sentrix.ai.eventlog.dto.EventLogRequestDto;
import com.sentrix.ai.eventlog.service.EventLogAnalysisService;
import jakarta.validation.Valid;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/event-logs")
public class EventLogController {

    private final EventLogAnalysisService eventLogAnalysisService;

    public EventLogController(EventLogAnalysisService eventLogAnalysisService) {
        this.eventLogAnalysisService = eventLogAnalysisService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<ApiResponse<EventLogAnalysisResponseDto>> analyze(
            @Valid @RequestBody List<EventLogRequestDto> events) {
        
        if (events == null || events.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.success(null, "Event list cannot be empty", MDC.get("correlationId")));
        }
        
        if (events.size() > 1000) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body(ApiResponse.success(null, "Cannot analyze more than 1000 events synchronously", MDC.get("correlationId")));
        }
        
        EventLogAnalysisResponseDto response = eventLogAnalysisService.analyze(events);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Event log analysis completed", MDC.get("correlationId")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EventLogAnalysisResponseDto>> getAnalysis(
            @PathVariable UUID id) {
        
        EventLogAnalysisResponseDto response = eventLogAnalysisService.getAnalysis(id);
        
        return ResponseEntity.ok(ApiResponse.success(response, "Analysis retrieved successfully", MDC.get("correlationId")));
    }
}
