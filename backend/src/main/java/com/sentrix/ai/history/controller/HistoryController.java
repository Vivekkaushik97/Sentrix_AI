package com.sentrix.ai.history.controller;

import com.sentrix.ai.common.ApiResponse;
import com.sentrix.ai.history.dto.HistoryResponseDto;
import com.sentrix.ai.history.service.HistoryService;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<HistoryResponseDto>>> getHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<HistoryResponseDto> historyPage = historyService.getHistory(pageRequest);
        
        return ResponseEntity.ok(ApiResponse.success(historyPage, "History retrieved successfully", MDC.get("correlationId")));
    }
}
