package com.sentrix.ai.rag;

public class Document {
    private String id;
    private String content;
    private String source;
    private String timestamp;

    public Document(String id, String content, String source, String timestamp) {
        this.id = id;
        this.content = content;
        this.source = source;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public String getContent() { return content; }
    public String getSource() { return source; }
    public String getTimestamp() { return timestamp; }
}
