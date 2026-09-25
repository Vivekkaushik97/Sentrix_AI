package com.sentrix.ai.windowsevent.dto;

import java.util.List;
import java.util.UUID;

public class WindowsEventAnalysisDetailDto {
    private UUID id;
    private int riskScore;
    private String severity;
    private String computerName;
    private List<WindowsEventDetectionDto> detections;
    private List<WindowsEventCorrelationDto> correlations;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public int getRiskScore() { return riskScore; }
    public void setRiskScore(int riskScore) { this.riskScore = riskScore; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public String getComputerName() { return computerName; }
    public void setComputerName(String computerName) { this.computerName = computerName; }
    public List<WindowsEventDetectionDto> getDetections() { return detections; }
    public void setDetections(List<WindowsEventDetectionDto> detections) { this.detections = detections; }
    public List<WindowsEventCorrelationDto> getCorrelations() { return correlations; }
    public void setCorrelations(List<WindowsEventCorrelationDto> correlations) { this.correlations = correlations; }
}
