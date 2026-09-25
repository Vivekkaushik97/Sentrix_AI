package com.sentrix.ai.security.event;

import com.sentrix.ai.common.enums.Severity;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

public class SecurityEvent {
    private String eventId;
    private SecurityEventType eventType;
    private SecurityEventSource source;
    private OffsetDateTime timestamp;
    private Severity severity;
    private String correlationId;
    private String entityReferenceId; // e.g., the User ID or IP address
    private Map<String, Object> payloadMetadata;
    
    public SecurityEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.timestamp = OffsetDateTime.now();
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public SecurityEventType getEventType() { return eventType; }
    public void setEventType(SecurityEventType eventType) { this.eventType = eventType; }
    public SecurityEventSource getSource() { return source; }
    public void setSource(SecurityEventSource source) { this.source = source; }
    public OffsetDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }
    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
    public String getEntityReferenceId() { return entityReferenceId; }
    public void setEntityReferenceId(String entityReferenceId) { this.entityReferenceId = entityReferenceId; }
    public Map<String, Object> getPayloadMetadata() { return payloadMetadata; }
    public void setPayloadMetadata(Map<String, Object> payloadMetadata) { this.payloadMetadata = payloadMetadata; }
}
