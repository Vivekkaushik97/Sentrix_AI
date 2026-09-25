package com.sentrix.ai.windowsevent.engine;

import com.sentrix.ai.windowsevent.entity.WindowsEventCorrelation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RiskScorer {

    public int calculateRiskScore(List<WindowsEventRule.RuleDetection> detections, List<WindowsEventCorrelation> correlations) {
        int score = 0;
        
        // Base score from detections
        for (WindowsEventRule.RuleDetection d : detections) {
            score += d.getRiskContribution();
        }
        
        // Correlations add significant multiplier risk
        for (WindowsEventCorrelation c : correlations) {
            switch (c.getSeverity()) {
                case CRITICAL: score += 50; break;
                case HIGH: score += 30; break;
                case MEDIUM: score += 15; break;
                case LOW: score += 5; break;
            }
        }
        
        // Clamp to 0-100
        return Math.max(0, Math.min(100, score));
    }
}
