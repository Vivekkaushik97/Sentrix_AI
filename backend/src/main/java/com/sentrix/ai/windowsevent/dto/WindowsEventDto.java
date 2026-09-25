package com.sentrix.ai.windowsevent.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class WindowsEventDto {
    private UUID id;
    private OffsetDateTime timestamp;
    private String computerName;
    private String logName;
    private String providerName;
    private Integer eventId;
    private String level;
    private String task;
    private String user;
    private String rawEvent;
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public OffsetDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
    public String getComputerName() { return computerName; }
    public void setComputerName(String computerName) { this.computerName = computerName; }
    public String getLogName() { return logName; }
    public void setLogName(String logName) { this.logName = logName; }
    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }
    public Integer getEventId() { return eventId; }
    public void setEventId(Integer eventId) { this.eventId = eventId; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getRawEvent() { return rawEvent; }
    public void setRawEvent(String rawEvent) { this.rawEvent = rawEvent; }
}
