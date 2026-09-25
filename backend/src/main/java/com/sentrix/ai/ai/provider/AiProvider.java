package com.sentrix.ai.ai.provider;

import com.sentrix.ai.ai.dto.AiRequestDto;
import com.sentrix.ai.ai.dto.AiResponseDto;

public interface AiProvider {
    AiResponseDto generateResponse(AiRequestDto request);
    boolean isConfigured();
}
