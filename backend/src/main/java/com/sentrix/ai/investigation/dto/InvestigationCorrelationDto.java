package com.sentrix.ai.investigation.dto;

public class InvestigationCorrelationDto {
    private String sourceRecordType;
    private String sourceRecordId;
    private String relatedRecordType;
    private String relatedRecordId;
    private String correlationReason;
    private Integer confidenceScore;

    public String getSourceRecordType() { return sourceRecordType; }
    public void setSourceRecordType(String sourceRecordType) { this.sourceRecordType = sourceRecordType; }
    public String getSourceRecordId() { return sourceRecordId; }
    public void setSourceRecordId(String sourceRecordId) { this.sourceRecordId = sourceRecordId; }
    public String getRelatedRecordType() { return relatedRecordType; }
    public void setRelatedRecordType(String relatedRecordType) { this.relatedRecordType = relatedRecordType; }
    public String getRelatedRecordId() { return relatedRecordId; }
    public void setRelatedRecordId(String relatedRecordId) { this.relatedRecordId = relatedRecordId; }
    public String getCorrelationReason() { return correlationReason; }
    public void setCorrelationReason(String correlationReason) { this.correlationReason = correlationReason; }
    public Integer getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Integer confidenceScore) { this.confidenceScore = confidenceScore; }
}
