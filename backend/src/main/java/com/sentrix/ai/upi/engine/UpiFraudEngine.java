package com.sentrix.ai.upi.engine;

import com.sentrix.ai.upi.entity.UpiTransaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UpiFraudEngine {

    private final List<UpiFraudRule> rules;

    public UpiFraudEngine(List<UpiFraudRule> rules) {
        this.rules = rules;
    }

    public List<UpiFraudRule.UpiRuleDetection> analyze(UpiTransaction transaction, List<UpiTransaction> contextHistory) {
        List<UpiFraudRule.UpiRuleDetection> detections = new ArrayList<>();
        
        for (UpiFraudRule rule : rules) {
            Optional<UpiFraudRule.UpiRuleDetection> detection = rule.evaluate(transaction, contextHistory);
            detection.ifPresent(detections::add);
        }
        
        return detections;
    }
}
