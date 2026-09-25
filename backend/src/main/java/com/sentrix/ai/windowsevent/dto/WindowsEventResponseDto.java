package com.sentrix.ai.windowsevent.dto;

import java.util.UUID;

public class WindowsEventResponseDto {
    private UUID analysisId;
    private int eventsAnalyzed;
    private int riskScore;
    private int detectionCount;
    private int correlationCount;
    private String status;

    public WindowsEventResponseDto(UUID analysisId, int eventsAnalyzed, int riskScore, int detectionCount, int correlationCount, String status) {
        this.analysisId = analysisId;
        this.eventsAnalyzed = eventsAnalyzed;
        this.riskScore = riskScore;
        this.detectionCount = detectionCount;
        this.correlationCount = correlationCount;
        this.status = status;
    }

    public UUID getAnalysisId() { return analysisId; }
    public int getEventsAnalyzed() { return eventsAnalyzed; }
    public int getRiskScore() { return riskScore; }
    public int getDetectionCount() { return detectionCount; }
    public int getCorrelationCount() { return correlationCount; }
    public String getStatus() { return status; }
}
