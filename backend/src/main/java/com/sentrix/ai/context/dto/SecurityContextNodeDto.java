package com.sentrix.ai.context.dto;

public class SecurityContextNodeDto {
    private String id;
    private String type; // UPI, WINDOWS, FRAUD, INCIDENT, INVESTIGATION
    private String label;
    private String severity;
    private Integer riskScore;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public Integer getRiskScore() { return riskScore; }
    public void setRiskScore(Integer riskScore) { this.riskScore = riskScore; }
}
