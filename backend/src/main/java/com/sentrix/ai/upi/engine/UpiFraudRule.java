package com.sentrix.ai.upi.engine;

import com.sentrix.ai.common.enums.Severity;
import com.sentrix.ai.upi.entity.UpiTransaction;
import java.util.List;
import java.util.Optional;

public interface UpiFraudRule {

    String getRuleId();
    String getDescription();
    Optional<UpiRuleDetection> evaluate(UpiTransaction transaction, List<UpiTransaction> history);

    class UpiRuleDetection {
        private final String ruleId;
        private final Severity severity;
        private final String reason;
        private final String evidence;
        private final int riskContribution;

        public UpiRuleDetection(String ruleId, Severity severity, String reason, String evidence, int riskContribution) {
            this.ruleId = ruleId;
            this.severity = severity;
            this.reason = reason;
            this.evidence = evidence;
            this.riskContribution = riskContribution;
        }

        public String getRuleId() { return ruleId; }
        public Severity getSeverity() { return severity; }
        public String getReason() { return reason; }
        public String getEvidence() { return evidence; }
        public int getRiskContribution() { return riskContribution; }
    }
}
