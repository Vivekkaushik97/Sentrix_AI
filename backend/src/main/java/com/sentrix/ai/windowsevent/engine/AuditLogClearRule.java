package com.sentrix.ai.windowsevent.engine;

import com.sentrix.ai.common.enums.Severity;
import com.sentrix.ai.windowsevent.entity.WindowsEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AuditLogClearRule implements WindowsEventRule {

    private static final String RULE_ID = "WIN-AUDIT-CLEAR";
    private static final int EVENT_ID_SYS_CLEAR = 104;
    private static final int EVENT_ID_SEC_CLEAR = 1102;

    @Override
    public String getRuleId() {
        return RULE_ID;
    }

    @Override
    public String getDescription() {
        return "Detects clearing of the Windows Event Log (Event ID 104 or 1102), which is often an indicator of defense evasion.";
    }

    @Override
    public boolean appliesTo(WindowsEvent event) {
        return event.getEventId() != null && 
               (event.getEventId() == EVENT_ID_SYS_CLEAR || event.getEventId() == EVENT_ID_SEC_CLEAR);
    }

    @Override
    public Optional<RuleDetection> evaluate(WindowsEvent event, List<WindowsEvent> contextEvents) {
        String reason = "Audit log was cleared. This is highly suspicious and often indicates defense evasion.";
        String evidence = String.format("Log Name: %s, Event ID: %d, User: %s, Time: %s", 
                event.getLogName(), event.getEventId(), event.getUser(), event.getTimestamp());
                
        return Optional.of(new RuleDetection(RULE_ID, Severity.CRITICAL, reason, evidence, 50));
    }
}
