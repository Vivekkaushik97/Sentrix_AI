package com.sentrix.ai.eventlog.engine;

import com.sentrix.ai.eventlog.dto.EventLogRequestDto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventLogRulesTest {

    @Test
    void failedAuthRule_ShouldTrigger_WhenThreeOrMoreFailures() {
        FailedAuthRule rule = new FailedAuthRule();
        
        EventLogRequestDto e1 = new EventLogRequestDto();
        e1.setEventType("AUTH"); e1.setStatus("FAILED");
        EventLogRequestDto e2 = new EventLogRequestDto();
        e2.setEventType("AUTH"); e2.setStatus("FAILED");
        EventLogRequestDto e3 = new EventLogRequestDto();
        e3.setEventType("AUTH"); e3.setStatus("FAILED");
        
        EventLogRule.RuleResult result = rule.evaluate(Arrays.asList(e1, e2, e3));
        
        assertTrue(result.isTriggered());
        assertEquals(40, result.getSeverityContribution());
    }

    @Test
    void privilegeEscalationRule_ShouldTrigger_OnSpecificEvent() {
        PrivilegeEscalationRule rule = new PrivilegeEscalationRule();
        
        EventLogRequestDto e1 = new EventLogRequestDto();
        e1.setEventType("PRIVILEGE_ESCALATION");
        
        EventLogRule.RuleResult result = rule.evaluate(List.of(e1));
        
        assertTrue(result.isTriggered());
        assertEquals(60, result.getSeverityContribution());
    }

    @Test
    void eventLogEngine_ShouldEvaluateCleanLogs() {
        EventLogEngine engine = new EventLogEngine(List.of(new FailedAuthRule(), new PrivilegeEscalationRule()));
        
        EventLogRequestDto e1 = new EventLogRequestDto();
        e1.setEventType("AUTH"); e1.setStatus("SUCCESS");
        
        EventLogEngine.EventLogEvaluationResult result = engine.evaluate(List.of(e1));
        
        assertEquals(0, result.getScore());
        assertEquals(0, result.getThreatsDetected());
        assertEquals("No suspicious indicators detected. Logs appear clean.", result.getFindings().get(0));
    }
}
