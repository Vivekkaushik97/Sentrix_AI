package com.sentrix.ai.investigation.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "investigation_events")
public class InvestigationEvent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investigation_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Investigation investigation;
    
    @Column(name = "event_type")
    private String eventType;
    
    @Column(name = "reference_id")
    private String referenceId;
    
    private String summary;
    
    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Investigation getInvestigation() { return investigation; }
    public void setInvestigation(Investigation investigation) { this.investigation = investigation; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getReferenceId() { return referenceId; }
    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}
