package com.sentrix.ai.windowsevent.engine;

import com.sentrix.ai.common.enums.Severity;
import com.sentrix.ai.windowsevent.entity.WindowsEvent;

import java.util.List;
import java.util.Optional;

public interface WindowsEventRule {

    String getRuleId();
    String getDescription();
    boolean appliesTo(WindowsEvent event);
    Optional<RuleDetection> evaluate(WindowsEvent event, List<WindowsEvent> contextEvents);

    class RuleDetection {
        private final String ruleId;
        private final Severity severity;
        private final String reason;
        private final String evidence;
        private final int riskContribution;

        public RuleDetection(String ruleId, Severity severity, String reason, String evidence, int riskContribution) {
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
