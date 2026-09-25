package com.sentrix.ai.windowsevent.controller;

import com.sentrix.ai.common.ApiResponse;
import com.sentrix.ai.windowsevent.dto.WindowsEventIngestionRequest;
import com.sentrix.ai.windowsevent.dto.WindowsEventResponseDto;
import com.sentrix.ai.windowsevent.service.WindowsEventIngestionService;
import jakarta.validation.Valid;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/windows-events")
public class WindowsEventController {

    private final WindowsEventIngestionService ingestionService;

    public WindowsEventController(WindowsEventIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping("/ingest")
    public ResponseEntity<ApiResponse<WindowsEventResponseDto>> ingest(
            @Valid @RequestBody WindowsEventIngestionRequest request) {

        WindowsEventResponseDto response = ingestionService.ingestAndAnalyze(request);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Windows events ingested and analyzed", MDC.get("correlationId")));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<org.springframework.data.domain.Page<com.sentrix.ai.windowsevent.dto.WindowsEventDto>>> getEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "timestamp"));
        return ResponseEntity.ok(ApiResponse.success(ingestionService.getEvents(pageable), "Events retrieved", MDC.get("correlationId")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<com.sentrix.ai.windowsevent.dto.WindowsEventAnalysisDetailDto>> getAnalysis(@PathVariable java.util.UUID id) {
        return ResponseEntity.ok(ApiResponse.success(ingestionService.getAnalysis(id), "Analysis retrieved", MDC.get("correlationId")));
    }

    @GetMapping("/detections")
    public ResponseEntity<ApiResponse<java.util.List<com.sentrix.ai.windowsevent.dto.WindowsEventDetectionDto>>> getDetections() {
        return ResponseEntity.ok(ApiResponse.success(ingestionService.getDetections(), "Detections retrieved", MDC.get("correlationId")));
    }

    @GetMapping("/correlations")
    public ResponseEntity<ApiResponse<java.util.List<com.sentrix.ai.windowsevent.dto.WindowsEventCorrelationDto>>> getCorrelations() {
        return ResponseEntity.ok(ApiResponse.success(ingestionService.getCorrelations(), "Correlations retrieved", MDC.get("correlationId")));
    }
}
