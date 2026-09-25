package com.sentrix.ai.investigation.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "investigation_correlations")
public class InvestigationCorrelation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investigation_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Investigation investigation;
    
    @Column(name = "source_record_type")
    private String sourceRecordType;
    
    @Column(name = "source_record_id")
    private String sourceRecordId;
    
    @Column(name = "related_record_type")
    private String relatedRecordType;
    
    @Column(name = "related_record_id")
    private String relatedRecordId;
    
    @Column(name = "correlation_reason")
    private String correlationReason;
    
    @Column(name = "confidence_score")
    private Integer confidenceScore;
    
    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Investigation getInvestigation() { return investigation; }
    public void setInvestigation(Investigation investigation) { this.investigation = investigation; }
    public String getSourceRecordType() { return sourceRecordType; }
    public void setSourceRecordType(String sourceRecordType) { this.sourceRecordType = sourceRecordType; }
    public String getSourceRecordId() { return sourceRecordId; }
    public void setSourceRecordId(String sourceRecordId) { this.sourceRecordId = sourceRecordId; }
    public String getRelatedRecordType() { return relatedRecordType; }
    public void setRelatedRecordType(String relatedRecordType) { this.relatedRecordType = relatedRecordType; }
    public String getRelatedRecordId() { return relatedRecordId; }
    public void setRelatedRecordId(String relatedRecordId) { this.relatedRecordId = relatedRecordId; }
    public String getCorrelationReason() { return correlationReason; }
    public void setCorrelationReason(String correlationReason) { this.correlationReason = correlationReason; }
    public Integer getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Integer confidenceScore) { this.confidenceScore = confidenceScore; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}
