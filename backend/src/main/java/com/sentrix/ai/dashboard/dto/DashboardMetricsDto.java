package com.sentrix.ai.dashboard.dto;

public class DashboardMetricsDto {
    private long totalAnalyses;
    private long highRiskAnalyses;
    private long fraudAnalyses;
    private long eventLogAnalyses;
    private long cveAnalyses;

    public long getTotalAnalyses() { return totalAnalyses; }
    public void setTotalAnalyses(long totalAnalyses) { this.totalAnalyses = totalAnalyses; }

    public long getHighRiskAnalyses() { return highRiskAnalyses; }
    public void setHighRiskAnalyses(long highRiskAnalyses) { this.highRiskAnalyses = highRiskAnalyses; }

    public long getFraudAnalyses() { return fraudAnalyses; }
    public void setFraudAnalyses(long fraudAnalyses) { this.fraudAnalyses = fraudAnalyses; }

    public long getEventLogAnalyses() { return eventLogAnalyses; }
    public void setEventLogAnalyses(long eventLogAnalyses) { this.eventLogAnalyses = eventLogAnalyses; }

    public long getCveAnalyses() { return cveAnalyses; }
    public void setCveAnalyses(long cveAnalyses) { this.cveAnalyses = cveAnalyses; }
}
