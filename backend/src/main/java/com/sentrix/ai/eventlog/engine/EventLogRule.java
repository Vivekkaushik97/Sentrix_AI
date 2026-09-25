package com.sentrix.ai.eventlog.engine;

import com.sentrix.ai.eventlog.dto.EventLogRequestDto;
import java.util.List;

public interface EventLogRule {
    RuleResult evaluate(List<EventLogRequestDto> events);
    
    class RuleResult {
        private final boolean triggered;
        private final String reason;
        private final int severityContribution;

        public RuleResult(boolean triggered, String reason, int severityContribution) {
            this.triggered = triggered;
            this.reason = reason;
            this.severityContribution = severityContribution;
        }

        public boolean isTriggered() { return triggered; }
        public String getReason() { return reason; }
        public int getSeverityContribution() { return severityContribution; }
    }
}
