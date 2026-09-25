package com.sentrix.ai.windowsevent.engine;

import com.sentrix.ai.common.enums.Severity;
import com.sentrix.ai.windowsevent.entity.WindowsEvent;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
public class FailedAuthRule implements WindowsEventRule {

    private static final String RULE_ID = "WIN-AUTH-4625";
    private static final int EVENT_ID_FAILED_AUTH = 4625;
    private static final int BURST_THRESHOLD = 5;

    @Override
    public String getRuleId() {
        return RULE_ID;
    }

    @Override
    public String getDescription() {
        return "Detects multiple failed authentication attempts (Event ID 4625) within a 5-minute window for the same user.";
    }

    @Override
    public boolean appliesTo(WindowsEvent event) {
        return event.getEventId() != null && event.getEventId() == EVENT_ID_FAILED_AUTH;
    }

    @Override
    public Optional<RuleDetection> evaluate(WindowsEvent event, List<WindowsEvent> contextEvents) {
        String user = event.getUser();
        if (user == null || user.isBlank()) {
            return Optional.empty(); // Cannot attribute to a user
        }

        // Count similar failures in the last 5 minutes
        long failureCount = contextEvents.stream()
                .filter(this::appliesTo)
                .filter(e -> user.equals(e.getUser()))
                .filter(e -> Math.abs(ChronoUnit.MINUTES.between(e.getTimestamp(), event.getTimestamp())) <= 5)
                .count();

        // Plus the current event
        if (failureCount + 1 >= BURST_THRESHOLD) {
            String reason = String.format("Detected %d failed authentication attempts for user '%s' within 5 minutes.", 
                    failureCount + 1, user);
            String evidence = "Event ID 4625 triggered at " + event.getTimestamp().toString();
            return Optional.of(new RuleDetection(RULE_ID, Severity.HIGH, reason, evidence, 30));
        }

        return Optional.empty();
    }
}
