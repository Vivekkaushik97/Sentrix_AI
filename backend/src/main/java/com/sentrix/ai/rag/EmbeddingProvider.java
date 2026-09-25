package com.sentrix.ai.rag;

import java.util.List;

public interface EmbeddingProvider {
    List<Double> generateEmbedding(String text);
}
