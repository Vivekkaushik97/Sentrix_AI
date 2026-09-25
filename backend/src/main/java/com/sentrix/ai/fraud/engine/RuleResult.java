package com.sentrix.ai.fraud.engine;

public class RuleResult {
    private final boolean triggered;
    private final String reason;
    private final int scoreContribution;

    public RuleResult(boolean triggered, String reason, int scoreContribution) {
        this.triggered = triggered;
        this.reason = reason;
        this.scoreContribution = scoreContribution;
    }

    public boolean isTriggered() { return triggered; }
    public String getReason() { return reason; }
    public int getScoreContribution() { return scoreContribution; }
}
