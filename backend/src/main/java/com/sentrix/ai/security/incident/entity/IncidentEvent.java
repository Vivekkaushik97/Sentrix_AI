package com.sentrix.ai.security.incident.entity;

import com.sentrix.ai.security.event.SecurityEventType;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "incident_events")
public class IncidentEvent {

    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incident_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private SecurityIncident incident;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private SecurityEventType eventType;

    @Column(name = "entity_reference_id", nullable = false)
    private String entityReferenceId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String reason;

    @Column(insertable = false, updatable = false)
    private OffsetDateTime timestamp;

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public SecurityIncident getIncident() { return incident; }
    public void setIncident(SecurityIncident incident) { this.incident = incident; }
    public SecurityEventType getEventType() { return eventType; }
    public void setEventType(SecurityEventType eventType) { this.eventType = eventType; }
    public String getEntityReferenceId() { return entityReferenceId; }
    public void setEntityReferenceId(String entityReferenceId) { this.entityReferenceId = entityReferenceId; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public OffsetDateTime getTimestamp() { return timestamp; }
}
