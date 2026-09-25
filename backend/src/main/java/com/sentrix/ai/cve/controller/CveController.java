package com.sentrix.ai.cve.controller;

import com.sentrix.ai.common.ApiResponse;
import com.sentrix.ai.cve.dto.CveSearchResponseDto;
import com.sentrix.ai.cve.service.CveIntelligenceService;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cves")
public class CveController {

    private final CveIntelligenceService cveIntelligenceService;

    public CveController(CveIntelligenceService cveIntelligenceService) {
        this.cveIntelligenceService = cveIntelligenceService;
    }

    @GetMapping("/{cveId}")
    public ResponseEntity<?> getCveDetail(@PathVariable String cveId) {
        if (!cveId.matches("^CVE-\\d{4}-\\d{4,}$")) {
            return ResponseEntity.badRequest()
                    .body(new com.sentrix.ai.common.ApiErrorResponse("BAD_REQUEST", "Invalid CVE format. Must be CVE-YYYY-NNNN", MDC.get("correlationId")));
        }
        
        CveSearchResponseDto response = cveIntelligenceService.getCveDetail(cveId);
        
        return ResponseEntity.ok(ApiResponse.success(response, "CVE detail retrieved successfully", MDC.get("correlationId")));
    }
}
