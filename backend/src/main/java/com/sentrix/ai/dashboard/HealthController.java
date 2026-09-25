package com.sentrix.ai.dashboard;

import com.sentrix.ai.common.ApiResponse;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/health")
    public ApiResponse<Map<String, String>> healthCheck() {
        String correlationId = MDC.get("correlationId");
        return ApiResponse.success(Map.of("status", "UP"), "System is operational", correlationId);
    }
}
