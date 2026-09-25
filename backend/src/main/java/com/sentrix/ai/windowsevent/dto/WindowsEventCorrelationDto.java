package com.sentrix.ai.windowsevent.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class WindowsEventCorrelationDto {
    private UUID id;
    private String correlationKey;
    private String explanation;
    private String severity;
    private OffsetDateTime createdAt;
    private List<WindowsEventDto> events;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCorrelationKey() { return correlationKey; }
    public void setCorrelationKey(String correlationKey) { this.correlationKey = correlationKey; }
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public List<WindowsEventDto> getEvents() { return events; }
    public void setEvents(List<WindowsEventDto> events) { this.events = events; }
}
