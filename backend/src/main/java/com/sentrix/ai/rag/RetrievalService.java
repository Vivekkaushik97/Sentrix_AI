package com.sentrix.ai.rag;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RetrievalService {

    private final VectorStore vectorStore;
    private final EmbeddingProvider embeddingProvider;

    public RetrievalService(VectorStore vectorStore, EmbeddingProvider embeddingProvider) {
        this.vectorStore = vectorStore;
        this.embeddingProvider = embeddingProvider;
    }

    public List<RetrievalResult> retrieveContext(String query) {
        // Retrieve embeddings for the query
        List<Double> queryEmbedding = embeddingProvider.generateEmbedding(query);
        
        // Return top 3 matches
        return vectorStore.search(queryEmbedding, 3);
    }
}
