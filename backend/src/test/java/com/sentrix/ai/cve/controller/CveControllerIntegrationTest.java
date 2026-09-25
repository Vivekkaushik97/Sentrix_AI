package com.sentrix.ai.cve.controller;

import com.sentrix.ai.common.exception.ResourceNotFoundException;
import com.sentrix.ai.cve.dto.CveSearchResponseDto;
import com.sentrix.ai.cve.service.CveIntelligenceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CveController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class CveControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CveIntelligenceService cveIntelligenceService;

    @Test
    void shouldReturnCveDetail() throws Exception {
        CveSearchResponseDto mockResponse = new CveSearchResponseDto();
        mockResponse.setCveId("CVE-2021-44228");
        mockResponse.setDescription("Log4j vulnerability");
        mockResponse.setCvssScore(java.math.BigDecimal.valueOf(10.0));
        mockResponse.setSeverity("CRITICAL");
        
        Mockito.when(cveIntelligenceService.getCveDetail("CVE-2021-44228")).thenReturn(mockResponse);
        
        mockMvc.perform(get("/api/v1/cves/CVE-2021-44228"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.cveId").value("CVE-2021-44228"))
                .andExpect(jsonPath("$.data.cvssScore").value(10.0));
    }

    @Test
    void shouldReturnBadRequestForInvalidFormat() throws Exception {
        mockMvc.perform(get("/api/v1/cves/INVALID-FORMAT"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid CVE format. Must be CVE-YYYY-NNNN"));
    }

    @Test
    void shouldReturnNotFoundForUnknownCve() throws Exception {
        Mockito.when(cveIntelligenceService.getCveDetail(anyString()))
                .thenThrow(new ResourceNotFoundException("CVE not found"));
                
        mockMvc.perform(get("/api/v1/cves/CVE-2099-0001"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }
}
