package com.sentrix.ai.fraud.dto;

import com.sentrix.ai.common.enums.AnalysisStatus;
import com.sentrix.ai.common.enums.Severity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class FraudAnalysisResponseDto {

    private UUID id;
    private AnalysisStatus status;
    private Severity severity;
    private LocalDateTime createdAt;
    
    private String transactionId;
    private BigDecimal amount;
    private String currency;
    private Integer riskScore;
    private Boolean isSuspicious;
    
    private List<String> findings;

    // Getters and Setters

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public AnalysisStatus getStatus() { return status; }
    public void setStatus(AnalysisStatus status) { this.status = status; }

    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public Integer getRiskScore() { return riskScore; }
    public void setRiskScore(Integer riskScore) { this.riskScore = riskScore; }

    public Boolean getIsSuspicious() { return isSuspicious; }
    public void setIsSuspicious(Boolean isSuspicious) { this.isSuspicious = isSuspicious; }

    public List<String> getFindings() { return findings; }
    public void setFindings(List<String> findings) { this.findings = findings; }
}
