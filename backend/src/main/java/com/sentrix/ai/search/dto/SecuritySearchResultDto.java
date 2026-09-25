package com.sentrix.ai.search.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class SecuritySearchResultDto {
    private String id;
    private String type; // e.g., INCIDENT, INVESTIGATION, ACTION
    private String title;
    private String status;
    private String priority;
    private OffsetDateTime timestamp;

    public SecuritySearchResultDto() {}

    public SecuritySearchResultDto(String id, String type, String title, String status, String priority, OffsetDateTime timestamp) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public OffsetDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
}
