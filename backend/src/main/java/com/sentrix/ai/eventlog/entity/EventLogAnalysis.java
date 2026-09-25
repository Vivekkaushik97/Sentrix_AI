package com.sentrix.ai.eventlog.entity;

import com.sentrix.ai.common.entity.Analysis;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_log_analyses")
public class EventLogAnalysis extends Analysis {

    private String logSource;
    private Integer totalEvents = 0;
    private Integer threatsDetected = 0;
    
    @Column(columnDefinition = "TEXT")
    private String rawLog;
    
    @Column(columnDefinition = "TEXT")
    private String findings;

    // Getters and Setters

    public String getLogSource() { return logSource; }
    public void setLogSource(String logSource) { this.logSource = logSource; }

    public Integer getTotalEvents() { return totalEvents; }
    public void setTotalEvents(Integer totalEvents) { this.totalEvents = totalEvents; }

    public Integer getThreatsDetected() { return threatsDetected; }
    public void setThreatsDetected(Integer threatsDetected) { this.threatsDetected = threatsDetected; }

    public String getRawLog() { return rawLog; }
    public void setRawLog(String rawLog) { this.rawLog = rawLog; }

    public String getFindings() { return findings; }
    public void setFindings(String findings) { this.findings = findings; }
}
