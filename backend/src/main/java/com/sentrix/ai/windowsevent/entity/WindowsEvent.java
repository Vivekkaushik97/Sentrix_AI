package com.sentrix.ai.windowsevent.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "windows_events")
public class WindowsEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private OffsetDateTime timestamp;

    @Column(name = "computer_name", nullable = false)
    private String computerName;

    @Column(name = "log_name", nullable = false)
    private String logName;

    @Column(name = "provider_name")
    private String providerName;

    @Column(name = "event_id", nullable = false)
    private Integer eventId;

    private String level;
    private String task;
    private String opcode;
    private String keywords;

    @Column(name = "system_user")
    private String user;

    @Column(name = "process_id")
    private Long processId;

    @Column(name = "thread_id")
    private Long threadId;

    private String channel;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "raw_event", columnDefinition = "TEXT")
    private String rawEvent;

    private String source;

    @Column(name = "ingestion_timestamp", nullable = false)
    private OffsetDateTime ingestionTimestamp;

    @PrePersist
    protected void onCreate() {
        if (ingestionTimestamp == null) {
            ingestionTimestamp = OffsetDateTime.now();
        }
    }

    // Getters and Setters
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
    public String getRawEvent() { return rawEvent; }
    public void setRawEvent(String rawEvent) { this.rawEvent = rawEvent; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public OffsetDateTime getIngestionTimestamp() { return ingestionTimestamp; }
    public void setIngestionTimestamp(OffsetDateTime ingestionTimestamp) { this.ingestionTimestamp = ingestionTimestamp; }
}
