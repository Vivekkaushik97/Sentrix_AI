package com.sentrix.ai.security.incident.dto;

import com.sentrix.ai.common.enums.Severity;
import com.sentrix.ai.security.event.SecurityEventType;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class SecurityIncidentDto {
    private UUID id;
    private String title;
    private String description;
    private Severity severity;
    private String status;
    private Integer riskScore;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<IncidentEventDto> events;

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getRiskScore() { return riskScore; }
    public void setRiskScore(Integer riskScore) { this.riskScore = riskScore; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
    public List<IncidentEventDto> getEvents() { return events; }
    public void setEvents(List<IncidentEventDto> events) { this.events = events; }
}

class IncidentEventDto {
    private UUID id;
    private SecurityEventType eventType;
    private String entityReferenceId;
    private String reason;
    private OffsetDateTime timestamp;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public SecurityEventType getEventType() { return eventType; }
    public void setEventType(SecurityEventType eventType) { this.eventType = eventType; }
    public String getEntityReferenceId() { return entityReferenceId; }
    public void setEntityReferenceId(String entityReferenceId) { this.entityReferenceId = entityReferenceId; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public OffsetDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
}
