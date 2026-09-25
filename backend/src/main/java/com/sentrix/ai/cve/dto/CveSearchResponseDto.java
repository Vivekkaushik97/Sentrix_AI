package com.sentrix.ai.cve.dto;

import java.time.LocalDateTime;

public class CveSearchResponseDto {

    private String cveId;
    private String description;
    private java.math.BigDecimal cvssScore;
    private String severity;
    private LocalDateTime publishedDate;
    private LocalDateTime lastModifiedDate;

    // Getters and Setters

    public String getCveId() { return cveId; }
    public void setCveId(String cveId) { this.cveId = cveId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public java.math.BigDecimal getCvssScore() { return cvssScore; }
    public void setCvssScore(java.math.BigDecimal cvssScore) { this.cvssScore = cvssScore; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public LocalDateTime getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDateTime publishedDate) { this.publishedDate = publishedDate; }

    public LocalDateTime getLastModifiedDate() { return lastModifiedDate; }
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) { this.lastModifiedDate = lastModifiedDate; }
}
