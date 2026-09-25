package com.sentrix.ai.report.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class ReportRequestDto {

    @NotNull(message = "Analysis ID is required")
    private UUID analysisId;
    
    private String title;

    public UUID getAnalysisId() { return analysisId; }
    public void setAnalysisId(UUID analysisId) { this.analysisId = analysisId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
