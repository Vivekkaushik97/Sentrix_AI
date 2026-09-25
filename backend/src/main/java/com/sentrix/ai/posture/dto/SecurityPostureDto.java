package com.sentrix.ai.posture.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public class SecurityPostureDto {
    private UUID id;
    private BigDecimal overallScore;
    private BigDecimal fraudRisk;
    private BigDecimal endpointRisk;
    private BigDecimal vulnerabilityRisk;
    private BigDecimal incidentRisk;
    private BigDecimal investigationRisk;
    private BigDecimal actionRisk;
    private ZonedDateTime createdAt;

    // Getters and Setters
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
