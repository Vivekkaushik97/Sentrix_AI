package com.sentrix.ai.fraud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentrix.ai.fraud.dto.FraudAnalysisRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@WebMvcTest(FraudController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class FraudControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private com.sentrix.ai.fraud.service.FraudAnalysisService fraudAnalysisService;

    @Test
    void shouldAnalyzeTransactionAndReturnSuspicious() throws Exception {
        FraudAnalysisRequestDto request = new FraudAnalysisRequestDto();
        request.setTransactionId("TXN-12345");
        request.setAmount(new BigDecimal("100000"));
        request.setCurrency("INR");
        request.setTransactionTimestamp(LocalDateTime.of(2023, 10, 10, 2, 0));
        request.setSenderInfo("sender@test.com");
        request.setReceiverInfo("receiver@test.com");
        request.setDeviceInfo("New device");

        com.sentrix.ai.fraud.dto.FraudAnalysisResponseDto mockResponse = new com.sentrix.ai.fraud.dto.FraudAnalysisResponseDto();
        mockResponse.setIsSuspicious(true);
        mockResponse.setRiskScore(95);
        Mockito.when(fraudAnalysisService.analyzeTransaction(any())).thenReturn(mockResponse);
        
        mockMvc.perform(post("/api/v1/fraud/analyze")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.isSuspicious").value(true))
                .andExpect(jsonPath("$.data.riskScore").value(95));
    }
}
