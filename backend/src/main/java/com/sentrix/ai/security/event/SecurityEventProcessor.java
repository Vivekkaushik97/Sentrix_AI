package com.sentrix.ai.security.event;

public interface SecurityEventProcessor {
    boolean supports(SecurityEventType eventType);
    void process(SecurityEvent event);
}
