package com.sentrix.ai.investigation.dto;

public class InvestigationEventDto {
    private String eventType;
    private String referenceId;
    private String summary;

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getReferenceId() { return referenceId; }
    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
}
