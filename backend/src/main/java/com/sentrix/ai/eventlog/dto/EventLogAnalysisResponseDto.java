package com.sentrix.ai.eventlog.dto;

import com.sentrix.ai.common.enums.AnalysisStatus;
import com.sentrix.ai.common.enums.Severity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EventLogAnalysisResponseDto {

    private UUID id;
    private AnalysisStatus status;
    private Severity severity;
    private LocalDateTime createdAt;
    
    private String logSource;
    private Integer totalEvents;
    private Integer threatsDetected;
    private List<String> findings;
    private Integer score;

    // Getters and Setters

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public AnalysisStatus getStatus() { return status; }
    public void setStatus(AnalysisStatus status) { this.status = status; }

    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getLogSource() { return logSource; }
    public void setLogSource(String logSource) { this.logSource = logSource; }

    public Integer getTotalEvents() { return totalEvents; }
    public void setTotalEvents(Integer totalEvents) { this.totalEvents = totalEvents; }

    public Integer getThreatsDetected() { return threatsDetected; }
    public void setThreatsDetected(Integer threatsDetected) { this.threatsDetected = threatsDetected; }

    public List<String> getFindings() { return findings; }
    public void setFindings(List<String> findings) { this.findings = findings; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
}
