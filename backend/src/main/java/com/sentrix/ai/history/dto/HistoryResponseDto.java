package com.sentrix.ai.history.dto;

import com.sentrix.ai.common.enums.AnalysisStatus;
import com.sentrix.ai.common.enums.AnalysisType;
import com.sentrix.ai.common.enums.Severity;

import java.time.LocalDateTime;
import java.util.UUID;

public class HistoryResponseDto {
    private UUID id;
    private AnalysisType type;
    private AnalysisStatus status;
    private Severity severity;
    private LocalDateTime createdAt;
    private String errorMessage;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public AnalysisType getType() { return type; }
    public void setType(AnalysisType type) { this.type = type; }

    public AnalysisStatus getStatus() { return status; }
    public void setStatus(AnalysisStatus status) { this.status = status; }

    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
