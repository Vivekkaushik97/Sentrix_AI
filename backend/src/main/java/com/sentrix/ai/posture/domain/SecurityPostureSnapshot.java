package com.sentrix.ai.posture.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "security_posture_snapshots")
public class SecurityPostureSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "overall_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal overallScore;

    @Column(name = "fraud_risk", nullable = false, precision = 5, scale = 2)
    private BigDecimal fraudRisk;

    @Column(name = "endpoint_risk", nullable = false, precision = 5, scale = 2)
    private BigDecimal endpointRisk;

    @Column(name = "vulnerability_risk", nullable = false, precision = 5, scale = 2)
    private BigDecimal vulnerabilityRisk;

    @Column(name = "incident_risk", nullable = false, precision = 5, scale = 2)
    private BigDecimal incidentRisk;

    @Column(name = "investigation_risk", nullable = false, precision = 5, scale = 2)
    private BigDecimal investigationRisk;

    @Column(name = "action_risk", nullable = false, precision = 5, scale = 2)
    private BigDecimal actionRisk;

    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = ZonedDateTime.now();
        }
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public BigDecimal getOverallScore() { return overallScore; }
    public void setOverallScore(BigDecimal overallScore) { this.overallScore = overallScore; }
    
    public BigDecimal getFraudRisk() { return fraudRisk; }
    public void setFraudRisk(BigDecimal fraudRisk) { this.fraudRisk = fraudRisk; }
    
    public BigDecimal getEndpointRisk() { return endpointRisk; }
    public void setEndpointRisk(BigDecimal endpointRisk) { this.endpointRisk = endpointRisk; }
    
    public BigDecimal getVulnerabilityRisk() { return vulnerabilityRisk; }
    public void setVulnerabilityRisk(BigDecimal vulnerabilityRisk) { this.vulnerabilityRisk = vulnerabilityRisk; }
    
    public BigDecimal getIncidentRisk() { return incidentRisk; }
    public void setIncidentRisk(BigDecimal incidentRisk) { this.incidentRisk = incidentRisk; }
    
    public BigDecimal getInvestigationRisk() { return investigationRisk; }
    public void setInvestigationRisk(BigDecimal investigationRisk) { this.investigationRisk = investigationRisk; }
    
    public BigDecimal getActionRisk() { return actionRisk; }
    public void setActionRisk(BigDecimal actionRisk) { this.actionRisk = actionRisk; }
    
    public ZonedDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(ZonedDateTime createdAt) { this.createdAt = createdAt; }
}
