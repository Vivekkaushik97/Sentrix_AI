package com.sentrix.ai.rag;

import java.util.List;

public interface VectorStore {
    void storeChunk(DocumentChunk chunk, List<Double> embedding);
    List<RetrievalResult> search(List<Double> queryEmbedding, int maxResults);
}
