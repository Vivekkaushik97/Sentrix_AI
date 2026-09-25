package com.sentrix.ai.eventlog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentrix.ai.eventlog.dto.EventLogRequestDto;
import com.sentrix.ai.eventlog.dto.EventLogAnalysisResponseDto;
import com.sentrix.ai.eventlog.service.EventLogAnalysisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventLogController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class EventLogControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventLogAnalysisService eventLogAnalysisService;

    @Test
    void shouldAnalyzeLogsAndReturnResult() throws Exception {
        EventLogRequestDto request = new EventLogRequestDto();
        request.setTimestamp(LocalDateTime.now());
        request.setEventType("AUTH");
        request.setSource("192.168.1.1");
        request.setAction("LOGIN");
        request.setStatus("FAILED");
        
        EventLogAnalysisResponseDto mockResponse = new EventLogAnalysisResponseDto();
        mockResponse.setThreatsDetected(1);
        mockResponse.setScore(40);
        
        Mockito.when(eventLogAnalysisService.analyze(any())).thenReturn(mockResponse);
        
        mockMvc.perform(post("/api/v1/event-logs/analyze")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(request))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.threatsDetected").value(1));
    }
}
