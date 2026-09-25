package com.sentrix.ai.alert.service;

import com.sentrix.ai.alert.enums.AlertPriority;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AlertPrioritizationService {

    public AlertPriority calculatePriority(
            String baseSeverity,
            BigDecimal riskScore,
            int correlationCount,
            boolean hasActiveIncident,
            boolean hasActiveInvestigation) {

        int score = 0;

        // Base severity mapping
        if (baseSeverity != null) {
            switch (baseSeverity.toUpperCase()) {
                case "CRITICAL":
                case "SEVERE":
                    score += 50;
                    break;
                case "HIGH":
                case "ERROR":
                    score += 30;
                    break;
                case "MEDIUM":
                case "WARNING":
                    score += 15;
                    break;
                case "LOW":
                    score += 5;
                    break;
                default:
                    score += 0;
            }
        }

        // Risk Score
        if (riskScore != null) {
            if (riskScore.compareTo(new BigDecimal("80")) >= 0) score += 40;
            else if (riskScore.compareTo(new BigDecimal("50")) >= 0) score += 20;
            else if (riskScore.compareTo(new BigDecimal("20")) >= 0) score += 10;
        }

        // Correlations
        if (correlationCount > 10) score += 20;
        else if (correlationCount > 3) score += 10;

        // Existing tracking
        if (hasActiveIncident) score += 30;
        if (hasActiveInvestigation) score += 15;

        // Evaluate deterministic threshold
        if (score >= 80) return AlertPriority.CRITICAL;
        if (score >= 50) return AlertPriority.HIGH;
        if (score >= 25) return AlertPriority.MEDIUM;
        if (score >= 10) return AlertPriority.LOW;
        
        return AlertPriority.INFORMATIONAL;
    }
}
