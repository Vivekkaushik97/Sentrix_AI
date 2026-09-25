package com.sentrix.ai.ai.provider;

import com.sentrix.ai.ai.dto.AiRequestDto;
import com.sentrix.ai.ai.dto.AiResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DefaultMockAiProvider implements AiProvider {

    @Value("${ai.provider.key:#{null}}")
    private String apiKey;

    @Override
    public boolean isConfigured() {
        return apiKey != null && !apiKey.isBlank();
    }

    @Override
    public AiResponseDto generateResponse(AiRequestDto request) {
        AiResponseDto response = new AiResponseDto();
        response.setPrompt(request.getPrompt());
        response.setTimestamp(LocalDateTime.now());
        
        if (isConfigured()) {
            response.setAnswer("AI is configured but this is a placeholder implementation. Context passed: " + request.getPrompt());
            response.setConfidence("HIGH");
        } else {
            if (request.getPrompt().contains("No security analyses found")) {
                response.setAnswer("I cannot answer your question because there is insufficient data in the system.");
                response.setConfidence("NONE");
            } else {
                response.setAnswer("AI Provider is currently unconfigured. Based on the provided context, the system has recent analysis data.");
                response.setConfidence("LOW");
            }
        }
        
        return response;
    }
}
