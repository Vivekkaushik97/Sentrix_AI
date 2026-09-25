package com.sentrix.ai.eventlog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class EventLogRequestDto {

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;
    
    @NotBlank(message = "Event type is required")
    private String eventType;
    
    @NotBlank(message = "Source is required")
    private String source;
    
    private String sourceIp;
    private String username;
    
    @NotBlank(message = "Action is required")
    private String action;
    
    @NotBlank(message = "Status/Result is required")
    private String status;
    
    private String resource;
    private String message;

    // Getters and Setters

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getSourceIp() { return sourceIp; }
    public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getResource() { return resource; }
    public void setResource(String resource) { this.resource = resource; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
