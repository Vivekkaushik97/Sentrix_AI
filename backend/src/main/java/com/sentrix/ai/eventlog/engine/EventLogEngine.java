package com.sentrix.ai.eventlog.engine;

import com.sentrix.ai.eventlog.dto.EventLogRequestDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventLogEngine {

    private final List<EventLogRule> rules;

    public EventLogEngine(List<EventLogRule> rules) {
        this.rules = rules;
    }

    public EventLogEvaluationResult evaluate(List<EventLogRequestDto> events) {
        int totalScore = 0;
        int threats = 0;
        List<String> findings = new ArrayList<>();

        for (EventLogRule rule : rules) {
            EventLogRule.RuleResult result = rule.evaluate(events);
            if (result.isTriggered()) {
                totalScore += result.getSeverityContribution();
                findings.add(result.getReason());
                threats++;
            }
        }

        totalScore = Math.min(totalScore, 100);

        if (totalScore == 0 && findings.isEmpty()) {
            findings.add("No suspicious indicators detected. Logs appear clean.");
        }

        return new EventLogEvaluationResult(totalScore, threats, findings);
    }

    public static class EventLogEvaluationResult {
        private final int score;
        private final int threatsDetected;
        private final List<String> findings;

        public EventLogEvaluationResult(int score, int threatsDetected, List<String> findings) {
            this.score = score;
            this.threatsDetected = threatsDetected;
            this.findings = findings;
        }

        public int getScore() { return score; }
        public int getThreatsDetected() { return threatsDetected; }
        public List<String> getFindings() { return findings; }
    }
}
