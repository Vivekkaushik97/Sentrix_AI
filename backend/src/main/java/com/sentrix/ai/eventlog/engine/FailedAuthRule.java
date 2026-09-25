package com.sentrix.ai.eventlog.engine;

import com.sentrix.ai.eventlog.dto.EventLogRequestDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FailedAuthRule implements EventLogRule {

    @Override
    public RuleResult evaluate(List<EventLogRequestDto> events) {
        long failedCount = events.stream()
                .filter(e -> "AUTH".equalsIgnoreCase(e.getEventType()) && "FAILED".equalsIgnoreCase(e.getStatus()))
                .count();

        if (failedCount >= 3) {
            return new RuleResult(true, "Repeated authentication failures detected (" + failedCount + " attempts).", 40);
        } else if (failedCount > 0) {
            return new RuleResult(true, "Authentication failure detected.", 10);
        }
        
        return new RuleResult(false, null, 0);
    }
}
