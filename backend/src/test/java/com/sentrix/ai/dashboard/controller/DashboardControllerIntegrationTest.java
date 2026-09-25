package com.sentrix.ai.dashboard.controller;

import com.sentrix.ai.dashboard.dto.DashboardMetricsDto;
import com.sentrix.ai.dashboard.service.DashboardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DashboardController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class DashboardControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DashboardService dashboardService;

    @Test
    void shouldReturnDashboardMetrics() throws Exception {
        DashboardMetricsDto mockMetrics = new DashboardMetricsDto();
        mockMetrics.setTotalAnalyses(10);
        mockMetrics.setHighRiskAnalyses(2);
        
        Mockito.when(dashboardService.getMetrics()).thenReturn(mockMetrics);
        
        mockMvc.perform(get("/api/v1/dashboard/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.totalAnalyses").value(10))
                .andExpect(jsonPath("$.data.highRiskAnalyses").value(2));
    }
}
