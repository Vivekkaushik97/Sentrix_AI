package com.sentrix.ai.ai.service;

import com.sentrix.ai.ai.dto.AiRequestDto;
import com.sentrix.ai.ai.dto.AiResponseDto;
import com.sentrix.ai.ai.dto.SecurityContext;
import com.sentrix.ai.ai.provider.AiProvider;
import org.springframework.stereotype.Service;

@Service
public class AiAssistantService {

    private final AiProvider aiProvider;
    private final SecurityContextBuilder contextBuilder;

    public AiAssistantService(AiProvider aiProvider, SecurityContextBuilder contextBuilder) {
        this.aiProvider = aiProvider;
        this.contextBuilder = contextBuilder;
    }

    public AiResponseDto ask(AiRequestDto request) {
        SecurityContext context = contextBuilder.buildContext(request.getPrompt());
        
        // Embellish the prompt with strict hallucination guards and context
        String safePrompt = "SYSTEM INSTRUCTION:\n"
                + "You are the Sentrix AI Intelligence Agent.\n"
                + "1. Only use the provided context below. Do NOT invent or fabricate facts.\n"
                + "2. If the context is insufficient, explicitly state 'Insufficient data to answer'.\n"
                + "\nCONTEXT:\n" + context.getAggregatedText() + "\n\n"
                + "USER QUESTION:\n" + request.getPrompt();
                
        AiRequestDto embellishedRequest = new AiRequestDto();
        embellishedRequest.setPrompt(safePrompt);
        
        AiResponseDto response = aiProvider.generateResponse(embellishedRequest);
        
        // Overwrite the returned prompt so the user sees their original query
        response.setPrompt(request.getPrompt());
        response.setSources(context.getSources());
        response.setContextUsed(context.isHasSufficientData());
        
        return response;
    }
}
