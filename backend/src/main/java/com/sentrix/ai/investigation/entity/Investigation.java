package com.sentrix.ai.investigation.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "investigations")
public class Investigation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    private String title;
    
    private String description;
    
    private String status = "OPEN";
    
    private String priority = "MEDIUM";
    
    private String owner;
    
    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;
    
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
    
    @Column(name = "closed_at")
    private OffsetDateTime closedAt;
    
    @OneToMany(mappedBy = "investigation", cascade = CascadeType.ALL)
    private List<InvestigationEvent> events;

    @OneToMany(mappedBy = "investigation", cascade = CascadeType.ALL)
    private List<InvestigationCorrelation> correlations;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public OffsetDateTime getClosedAt() { return closedAt; }
    public void setClosedAt(OffsetDateTime closedAt) { this.closedAt = closedAt; }
    public List<InvestigationEvent> getEvents() { return events; }
    public void setEvents(List<InvestigationEvent> events) { this.events = events; }
    public List<InvestigationCorrelation> getCorrelations() { return correlations; }
    public void setCorrelations(List<InvestigationCorrelation> correlations) { this.correlations = correlations; }
}
