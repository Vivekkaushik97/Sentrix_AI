package com.sentrix.ai.investigation.dto;

import java.time.OffsetDateTime;

public class InvestigationTimelineDto {
    private String id;
    private OffsetDateTime timestamp;
    private String source;
    private String eventType;
    private String summary;
    private String severity;
    private Integer riskScore;
    private String relatedEntityId;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public OffsetDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public Integer getRiskScore() { return riskScore; }
    public void setRiskScore(Integer riskScore) { this.riskScore = riskScore; }
    public String getRelatedEntityId() { return relatedEntityId; }
    public void setRelatedEntityId(String relatedEntityId) { this.relatedEntityId = relatedEntityId; }
}
