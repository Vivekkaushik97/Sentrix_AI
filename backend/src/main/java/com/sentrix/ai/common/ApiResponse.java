package com.sentrix.ai.common;

import java.time.Instant;

public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private Instant timestamp;
    private String correlationId;

    public ApiResponse(boolean success, T data, String message, String correlationId) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.timestamp = Instant.now();
        this.correlationId = correlationId;
    }

    public static <T> ApiResponse<T> success(T data, String message, String correlationId) {
        return new ApiResponse<>(true, data, message, correlationId);
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
}
