package com.sentrix.ai.security.live;

import com.sentrix.ai.security.event.SecurityEvent;
import com.sentrix.ai.security.event.SecurityEventProcessor;
import com.sentrix.ai.security.event.SecurityEventType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class LiveEventService implements SecurityEventProcessor {

    private static final Logger logger = LoggerFactory.getLogger(LiveEventService.class);
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter createEmitter() {
        SseEmitter emitter = new SseEmitter(60000L); // 1 minute timeout for demo
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> {
            emitter.complete();
            emitters.remove(emitter);
        });
        emitter.onError(e -> {
            emitter.completeWithError(e);
            emitters.remove(emitter);
        });

        return emitter;
    }

    @Override
    public boolean supports(SecurityEventType eventType) {
        return true; // Broadcast all events
    }

    @Override
    public void process(SecurityEvent event) {
        List<SseEmitter> deadEmitters = new CopyOnWriteArrayList<>();
        
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("security-event")
                        .data(event)
                        .id(event.getEventId()));
            } catch (IOException e) {
                deadEmitters.add(emitter);
            }
        });
        
        emitters.removeAll(deadEmitters);
    }
}
