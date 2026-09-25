package com.sentrix.ai.history.controller;

import com.sentrix.ai.common.enums.AnalysisStatus;
import com.sentrix.ai.common.enums.AnalysisType;
import com.sentrix.ai.common.enums.Severity;
import com.sentrix.ai.history.dto.HistoryResponseDto;
import com.sentrix.ai.history.service.HistoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HistoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class HistoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistoryService historyService;

    @Test
    void shouldReturnPaginatedHistory() throws Exception {
        HistoryResponseDto dto = new HistoryResponseDto();
        dto.setId(UUID.randomUUID());
        dto.setType(AnalysisType.FRAUD);
        dto.setStatus(AnalysisStatus.COMPLETED);
        dto.setSeverity(Severity.HIGH);
        
        Mockito.when(historyService.getHistory(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));
                
        mockMvc.perform(get("/api/v1/history?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content[0].type").value("FRAUD"))
                .andExpect(jsonPath("$.data.content[0].severity").value("HIGH"));
    }
}
