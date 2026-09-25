package com.sentrix.ai.ai.dto;

import java.time.LocalDateTime;

public class AiResponseDto {
    private String prompt;
    private String answer;
    private LocalDateTime timestamp;
    private java.util.List<String> sources = new java.util.ArrayList<>();
    private String confidence;
    private boolean contextUsed;

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public java.util.List<String> getSources() { return sources; }
    public void setSources(java.util.List<String> sources) { this.sources = sources; }

    public String getConfidence() { return confidence; }
    public void setConfidence(String confidence) { this.confidence = confidence; }

    public boolean isContextUsed() { return contextUsed; }
    public void setContextUsed(boolean contextUsed) { this.contextUsed = contextUsed; }
}
