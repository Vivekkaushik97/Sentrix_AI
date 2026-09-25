package com.sentrix.ai.security.event;

public interface SecurityEventPublisher {
    void publish(SecurityEvent event);
}
