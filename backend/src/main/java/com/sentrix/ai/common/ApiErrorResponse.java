package com.sentrix.ai.common;

import java.time.Instant;

public class ApiErrorResponse {
    private boolean success;
    private String errorCode;
    private String message;
    private Instant timestamp;
    private String correlationId;

    public ApiErrorResponse(String errorCode, String message, String correlationId) {
        this.success = false;
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = Instant.now();
        this.correlationId = correlationId;
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
}
