package com.sentrix.ai.dashboard.controller;

import com.sentrix.ai.common.ApiResponse;
import com.sentrix.ai.dashboard.dto.DashboardMetricsDto;
import com.sentrix.ai.dashboard.service.DashboardService;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/metrics")
    public ResponseEntity<ApiResponse<DashboardMetricsDto>> getMetrics() {
        DashboardMetricsDto metrics = dashboardService.getMetrics();
        return ResponseEntity.ok(ApiResponse.success(metrics, "Dashboard metrics retrieved successfully", MDC.get("correlationId")));
    }
}
