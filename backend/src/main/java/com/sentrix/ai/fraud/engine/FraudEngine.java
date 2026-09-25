package com.sentrix.ai.fraud.engine;

import com.sentrix.ai.fraud.dto.FraudAnalysisRequestDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FraudEngine {

    private final List<FraudRule> rules;

    public FraudEngine(List<FraudRule> rules) {
        this.rules = rules;
    }

    public FraudEvaluationResult evaluate(FraudAnalysisRequestDto request) {
        int totalScore = 0;
        List<String> findings = new ArrayList<>();

        for (FraudRule rule : rules) {
            RuleResult result = rule.evaluate(request);
            if (result.isTriggered()) {
                totalScore += result.getScoreContribution();
                findings.add(result.getReason());
            }
        }

        // Cap score at 100
        totalScore = Math.min(totalScore, 100);

        return new FraudEvaluationResult(totalScore, findings);
    }

    public static class FraudEvaluationResult {
        private final int riskScore;
        private final List<String> findings;

        public FraudEvaluationResult(int riskScore, List<String> findings) {
            this.riskScore = riskScore;
            this.findings = findings;
        }

        public int getRiskScore() { return riskScore; }
        public List<String> getFindings() { return findings; }
    }
}
