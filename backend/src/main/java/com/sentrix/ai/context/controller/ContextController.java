package com.sentrix.ai.context.controller;

import com.sentrix.ai.common.ApiResponse;
import com.sentrix.ai.context.dto.SecurityContextDto;
import com.sentrix.ai.context.service.ContextResolutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/context")
public class ContextController {

    private final ContextResolutionService resolutionService;

    public ContextController(ContextResolutionService resolutionService) {
        this.resolutionService = resolutionService;
    }

    @GetMapping("/{sourceType}/{sourceId}")
    public ResponseEntity<ApiResponse<SecurityContextDto>> getContext(
            @PathVariable String sourceType,
            @PathVariable String sourceId) {
        
        SecurityContextDto context = resolutionService.resolveContext(sourceType, sourceId);
        return ResponseEntity.ok(ApiResponse.success(context, "Context resolved", UUID.randomUUID().toString()));
    }
}
