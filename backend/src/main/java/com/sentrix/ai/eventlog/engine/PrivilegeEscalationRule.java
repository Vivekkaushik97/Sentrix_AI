package com.sentrix.ai.eventlog.engine;

import com.sentrix.ai.eventlog.dto.EventLogRequestDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrivilegeEscalationRule implements EventLogRule {

    @Override
    public RuleResult evaluate(List<EventLogRequestDto> events) {
        boolean hasPrivilegeEvent = events.stream()
                .anyMatch(e -> "PRIVILEGE_ESCALATION".equalsIgnoreCase(e.getEventType()) 
                            || (e.getAction() != null && e.getAction().toLowerCase().contains("admin privilege")));

        if (hasPrivilegeEvent) {
            return new RuleResult(true, "Suspicious privilege escalation action detected.", 60);
        }
        
        return new RuleResult(false, null, 0);
    }
}
