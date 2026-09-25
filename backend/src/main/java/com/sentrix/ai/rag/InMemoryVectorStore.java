package com.sentrix.ai.rag;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryVectorStore implements VectorStore {

    private final List<DocumentChunk> store = new ArrayList<>();

    @Override
    public void storeChunk(DocumentChunk chunk, List<Double> embedding) {
        // Mock embedding storage
        store.add(chunk);
    }

    @Override
    public List<RetrievalResult> search(List<Double> queryEmbedding, int maxResults) {
        List<RetrievalResult> results = new ArrayList<>();
        // In a real system, we would calculate cosine similarity here.
        // For now, just return up to maxResults from the store.
        for (int i = 0; i < Math.min(store.size(), maxResults); i++) {
            DocumentChunk chunk = store.get(i);
            results.add(new RetrievalResult(chunk.getText(), chunk.getDocumentId(), 0.95 - (i * 0.05)));
        }
        return results;
    }
}
