package com.sentrix.ai.rag;

public class RetrievalResult {
    private String text;
    private String source;
    private double relevanceScore;

    public RetrievalResult(String text, String source, double relevanceScore) {
        this.text = text;
        this.source = source;
        this.relevanceScore = relevanceScore;
    }

    public String getText() { return text; }
    public String getSource() { return source; }
    public double getRelevanceScore() { return relevanceScore; }
}
