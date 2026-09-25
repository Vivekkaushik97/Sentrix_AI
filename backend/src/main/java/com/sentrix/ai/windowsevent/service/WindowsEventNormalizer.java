package com.sentrix.ai.windowsevent.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentrix.ai.windowsevent.dto.RawWindowsEventDto;
import com.sentrix.ai.windowsevent.entity.WindowsEvent;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class WindowsEventNormalizer {

    private final ObjectMapper objectMapper;

    public WindowsEventNormalizer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public WindowsEvent normalize(RawWindowsEventDto rawDto, String computerName, String source) {
        WindowsEvent event = new WindowsEvent();
        
        // Normalize Timestamp
        event.setTimestamp(rawDto.getTimestamp() != null ? rawDto.getTimestamp() : OffsetDateTime.now());
        
        event.setComputerName(computerName);
        event.setSource(source);
        event.setLogName(rawDto.getLogName());
        event.setProviderName(rawDto.getProviderName());
        
        // Ensure EventID is captured
        event.setEventId(rawDto.getEventId() != null ? rawDto.getEventId() : 0);
        
        event.setLevel(rawDto.getLevel());
        event.setTask(rawDto.getTask());
        event.setOpcode(rawDto.getOpcode());
        event.setKeywords(rawDto.getKeywords());
        event.setUser(rawDto.getUser());
        event.setProcessId(rawDto.getProcessId());
        event.setThreadId(rawDto.getThreadId());
        event.setChannel(rawDto.getChannel());
        event.setMessage(rawDto.getMessage());
        
        // Serialize raw payload for deep audit and AI Context
        if (rawDto.getRawEventData() != null) {
            try {
                event.setRawEvent(objectMapper.writeValueAsString(rawDto.getRawEventData()));
            } catch (JsonProcessingException e) {
                event.setRawEvent("{ \"error\": \"Could not serialize raw event data\" }");
            }
        }
        
        return event;
    }
}
