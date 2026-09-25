package com.sentrix.ai.context.dto;

import java.util.List;

public class SecurityContextDto {
    private String source;
    private String entityId;
    private Integer riskScore;
    private String severity;
    private SecurityContextGraphDto graph;
    private List<com.sentrix.ai.investigation.dto.InvestigationTimelineDto> timeline;
    private List<String> provenance;

    // Getters and Setters
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getEntityId() { return entityId; }
    public void setEntityId(String entityId) { this.entityId = entityId; }
    public Integer getRiskScore() { return riskScore; }
    public void setRiskScore(Integer riskScore) { this.riskScore = riskScore; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public SecurityContextGraphDto getGraph() { return graph; }
    public void setGraph(SecurityContextGraphDto graph) { this.graph = graph; }
    public List<com.sentrix.ai.investigation.dto.InvestigationTimelineDto> getTimeline() { return timeline; }
    public void setTimeline(List<com.sentrix.ai.investigation.dto.InvestigationTimelineDto> timeline) { this.timeline = timeline; }
    public List<String> getProvenance() { return provenance; }
    public void setProvenance(List<String> provenance) { this.provenance = provenance; }
}
