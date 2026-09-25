package com.sentrix.ai.rag;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class MockEmbeddingProvider implements EmbeddingProvider {

    @Override
    public List<Double> generateEmbedding(String text) {
        // Return a mock vector
        return Collections.nCopies(1536, 0.1); // e.g. OpenAI ada-002 size
    }
}
