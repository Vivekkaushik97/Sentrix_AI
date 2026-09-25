package com.sentrix.ai.report.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentrix.ai.report.dto.ReportRequestDto;
import com.sentrix.ai.report.dto.ReportResponseDto;
import com.sentrix.ai.report.service.ReportService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ReportControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReportService reportService;

    @Test
    void shouldGenerateReport() throws Exception {
        UUID analysisId = UUID.randomUUID();
        ReportRequestDto request = new ReportRequestDto();
        request.setAnalysisId(analysisId);
        request.setTitle("Test Report");
        
        ReportResponseDto mockResponse = new ReportResponseDto();
        mockResponse.setId(UUID.randomUUID());
        mockResponse.setTitle("Test Report");
        mockResponse.setStatus("GENERATED");
        
        Mockito.when(reportService.generateReport(any(ReportRequestDto.class))).thenReturn(mockResponse);
        
        mockMvc.perform(post("/api/v1/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.title").value("Test Report"));
    }
}
