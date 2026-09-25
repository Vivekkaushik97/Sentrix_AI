package com.sentrix.ai.rag;

public class DocumentChunk {
    private String chunkId;
    private String documentId;
    private String text;

    public DocumentChunk(String chunkId, String documentId, String text) {
        this.chunkId = chunkId;
        this.documentId = documentId;
        this.text = text;
    }

    public String getChunkId() { return chunkId; }
    public String getDocumentId() { return documentId; }
    public String getText() { return text; }
}
