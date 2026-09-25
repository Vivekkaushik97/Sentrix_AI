package com.sentrix.ai.upi.engine;

import com.sentrix.ai.upi.entity.UpiTransaction;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UpiRiskScorer {

    public int calculateRiskScore(UpiTransaction transaction, List<UpiFraudRule.UpiRuleDetection> detections) {
        int score = 0;
        
        // Base score from detections
        for (UpiFraudRule.UpiRuleDetection d : detections) {
            score += d.getRiskContribution();
        }

        // Amount based heuristic (large transactions add slight base risk)
        if (transaction.getAmount().doubleValue() > 50000) {
            score += 10;
        }

        // Clamp to 0-100
        return Math.max(0, Math.min(100, score));
    }
}
