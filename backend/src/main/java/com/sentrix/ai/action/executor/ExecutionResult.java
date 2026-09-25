package com.sentrix.ai.action.executor;

public class ExecutionResult {
    private boolean successful;
    private String resultSummary;
    private String failureReason;

    public static ExecutionResult success(String summary) {
        ExecutionResult r = new ExecutionResult();
        r.successful = true;
        r.resultSummary = summary;
        return r;
    }

    public static ExecutionResult failure(String reason) {
        ExecutionResult r = new ExecutionResult();
        r.successful = false;
        r.failureReason = reason;
        return r;
    }

    public boolean isSuccessful() { return successful; }
    public String getResultSummary() { return resultSummary; }
    public String getFailureReason() { return failureReason; }
}
