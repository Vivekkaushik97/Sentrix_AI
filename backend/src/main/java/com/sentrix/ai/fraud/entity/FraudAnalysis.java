package com.sentrix.ai.fraud.entity;

import com.sentrix.ai.common.entity.Analysis;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fraud_analyses")
public class FraudAnalysis extends Analysis {

    private String transactionId;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal amount;
    
    private String currency;
    private LocalDateTime transactionTimestamp;
    private String senderInfo;
    private String receiverInfo;
    private String deviceInfo;
    private String ipAddress;
    private String location;
    private String paymentChannel;
    private Integer riskScore;
    
    @Column(columnDefinition = "TEXT")
    private String findings;
    
    private Boolean isSuspicious;

    // Getters and Setters

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public LocalDateTime getTransactionTimestamp() { return transactionTimestamp; }
    public void setTransactionTimestamp(LocalDateTime transactionTimestamp) { this.transactionTimestamp = transactionTimestamp; }

    public String getSenderInfo() { return senderInfo; }
    public void setSenderInfo(String senderInfo) { this.senderInfo = senderInfo; }

    public String getReceiverInfo() { return receiverInfo; }
    public void setReceiverInfo(String receiverInfo) { this.receiverInfo = receiverInfo; }

    public String getDeviceInfo() { return deviceInfo; }
    public void setDeviceInfo(String deviceInfo) { this.deviceInfo = deviceInfo; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getPaymentChannel() { return paymentChannel; }
    public void setPaymentChannel(String paymentChannel) { this.paymentChannel = paymentChannel; }

    public Integer getRiskScore() { return riskScore; }
    public void setRiskScore(Integer riskScore) { this.riskScore = riskScore; }

    public String getFindings() { return findings; }
    public void setFindings(String findings) { this.findings = findings; }

    public Boolean getIsSuspicious() { return isSuspicious; }
    public void setIsSuspicious(Boolean isSuspicious) { this.isSuspicious = isSuspicious; }
}
