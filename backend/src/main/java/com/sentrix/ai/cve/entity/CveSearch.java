package com.sentrix.ai.cve.entity;

import com.sentrix.ai.common.entity.Analysis;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "cve_searches")
public class CveSearch extends Analysis {

    private String cveId;
    private String keyword;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(precision = 4, scale = 1)
    private java.math.BigDecimal cvssScore;
    
    private LocalDateTime publishedDate;
    
    @Column(columnDefinition = "JSONB")
    private String rawResponse;
    
    @Column(columnDefinition = "TEXT")
    private String findings;

    // Getters and Setters

    public String getCveId() { return cveId; }
    public void setCveId(String cveId) { this.cveId = cveId; }

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public java.math.BigDecimal getCvssScore() { return cvssScore; }
    public void setCvssScore(java.math.BigDecimal cvssScore) { this.cvssScore = cvssScore; }

    public LocalDateTime getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDateTime publishedDate) { this.publishedDate = publishedDate; }

    public String getRawResponse() { return rawResponse; }
    public void setRawResponse(String rawResponse) { this.rawResponse = rawResponse; }

    public String getFindings() { return findings; }
    public void setFindings(String findings) { this.findings = findings; }
}
