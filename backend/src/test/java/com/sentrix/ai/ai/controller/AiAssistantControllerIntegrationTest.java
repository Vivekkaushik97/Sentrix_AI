package com.sentrix.ai.ai.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentrix.ai.ai.dto.AiRequestDto;
import com.sentrix.ai.ai.dto.AiResponseDto;
import com.sentrix.ai.ai.service.AiAssistantService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AiAssistantController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class AiAssistantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AiAssistantService aiAssistantService;

    @Test
    void shouldReturnAiResponse() throws Exception {
        AiRequestDto request = new AiRequestDto();
        request.setPrompt("What is a CVE?");
        
        AiResponseDto mockResponse = new AiResponseDto();
        mockResponse.setPrompt("What is a CVE?");
        mockResponse.setAnswer("AI Provider is currently unconfigured or unavailable. No real AI inference was performed.");
        
        Mockito.when(aiAssistantService.ask(any(AiRequestDto.class))).thenReturn(mockResponse);
        
        mockMvc.perform(post("/api/v1/ai/ask")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.answer").exists());
    }
}
