package com.sentrix.ai.windowsevent.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.OffsetDateTime;
import java.util.Map;

public class RawWindowsEventDto {

    private OffsetDateTime timestamp;
    
    @NotBlank(message = "Log name is required")
    private String logName;
    
    private String providerName;
    
    private Integer eventId;
    
    private String level;
    private String task;
    private String opcode;
    private String keywords;
    private String user;
    private Long processId;
    private Long threadId;
    private String channel;
    private String message;
    
    private Map<String, Object> rawEventData;

    // Getters and Setters
    public OffsetDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
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
    public String getOpcode() { return opcode; }
    public void setOpcode(String opcode) { this.opcode = opcode; }
    public String getKeywords() { return keywords; }
    public void setKeywords(String keywords) { this.keywords = keywords; }
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public Long getProcessId() { return processId; }
    public void setProcessId(Long processId) { this.processId = processId; }
    public Long getThreadId() { return threadId; }
    public void setThreadId(Long threadId) { this.threadId = threadId; }
    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Map<String, Object> getRawEventData() { return rawEventData; }
    public void setRawEventData(Map<String, Object> rawEventData) { this.rawEventData = rawEventData; }
}
