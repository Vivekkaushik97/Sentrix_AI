package com.sentrix.ai.ai.controller;

import com.sentrix.ai.ai.dto.AiRequestDto;
import com.sentrix.ai.ai.dto.AiResponseDto;
import com.sentrix.ai.ai.service.AiAssistantService;
import com.sentrix.ai.common.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
public class AiAssistantController {

    private final AiAssistantService aiAssistantService;

    public AiAssistantController(AiAssistantService aiAssistantService) {
        this.aiAssistantService = aiAssistantService;
    }

    @PostMapping("/ask")
    public ResponseEntity<ApiResponse<AiResponseDto>> askAssistant(@Valid @RequestBody AiRequestDto request) {
        AiResponseDto response = aiAssistantService.ask(request);
        return ResponseEntity.ok(ApiResponse.success(response, "AI response generated successfully", MDC.get("correlationId")));
    }
}
