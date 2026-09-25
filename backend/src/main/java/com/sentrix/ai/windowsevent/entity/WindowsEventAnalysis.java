package com.sentrix.ai.windowsevent.entity;

import com.sentrix.ai.common.entity.Analysis;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "windows_event_analyses")
@PrimaryKeyJoinColumn(name = "id")
public class WindowsEventAnalysis extends Analysis {

    @Column(name = "risk_score", nullable = false)
    private Integer riskScore;

    @Column(name = "computer_name")
    private String computerName;

    @OneToMany(mappedBy = "analysis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WindowsEventDetection> detections = new ArrayList<>();

    @OneToMany(mappedBy = "analysis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WindowsEventCorrelation> correlations = new ArrayList<>();

    public Integer getRiskScore() { return riskScore; }
    public void setRiskScore(Integer riskScore) { this.riskScore = riskScore; }
    public String getComputerName() { return computerName; }
    public void setComputerName(String computerName) { this.computerName = computerName; }
    public List<WindowsEventDetection> getDetections() { return detections; }
    public void setDetections(List<WindowsEventDetection> detections) { this.detections = detections; }
    public List<WindowsEventCorrelation> getCorrelations() { return correlations; }
    public void setCorrelations(List<WindowsEventCorrelation> correlations) { this.correlations = correlations; }
}
