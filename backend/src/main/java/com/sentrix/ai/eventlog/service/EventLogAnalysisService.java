package com.sentrix.ai.eventlog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentrix.ai.common.enums.AnalysisStatus;
import com.sentrix.ai.common.enums.AnalysisType;
import com.sentrix.ai.common.enums.Severity;
import com.sentrix.ai.common.exception.ResourceNotFoundException;
import com.sentrix.ai.eventlog.dto.EventLogAnalysisResponseDto;
import com.sentrix.ai.eventlog.dto.EventLogRequestDto;
import com.sentrix.ai.eventlog.engine.EventLogEngine;
import com.sentrix.ai.eventlog.entity.EventLogAnalysis;
import com.sentrix.ai.eventlog.repository.EventLogAnalysisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class EventLogAnalysisService {

    private final EventLogAnalysisRepository repository;
    private final EventLogEngine engine;
    private final ObjectMapper objectMapper;

    public EventLogAnalysisService(EventLogAnalysisRepository repository, EventLogEngine engine, ObjectMapper objectMapper) {
        this.repository = repository;
        this.engine = engine;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public EventLogAnalysisResponseDto analyze(List<EventLogRequestDto> events) {
        EventLogEngine.EventLogEvaluationResult evaluation = engine.evaluate(events);
        
        Severity severity = determineSeverity(evaluation.getScore());
        
        EventLogAnalysis analysis = new EventLogAnalysis();
        analysis.setType(AnalysisType.EVENT_LOG);
        analysis.setStatus(AnalysisStatus.COMPLETED);
        analysis.setSeverity(severity);
        
        analysis.setLogSource("JSON Batch Upload");
        analysis.setTotalEvents(events.size());
        analysis.setThreatsDetected(evaluation.getThreatsDetected());
        analysis.setFindings(String.join("||", evaluation.getFindings()));
        
        try {
            analysis.setRawLog(objectMapper.writeValueAsString(events));
        } catch (JsonProcessingException e) {
            analysis.setRawLog("[]");
        }
        
        EventLogAnalysis saved = repository.save(analysis);
        return mapToDto(saved, evaluation.getScore());
    }

    @Transactional(readOnly = true)
    public EventLogAnalysisResponseDto getAnalysis(UUID id) {
        EventLogAnalysis analysis = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event log analysis not found"));
                
        // Calculate score from findings severity (simplified for retrieval)
        int estimatedScore = 0;
        if (analysis.getSeverity() == Severity.CRITICAL) estimatedScore = 90;
        else if (analysis.getSeverity() == Severity.HIGH) estimatedScore = 70;
        else if (analysis.getSeverity() == Severity.MEDIUM) estimatedScore = 40;
        else if (analysis.getSeverity() == Severity.LOW) estimatedScore = 15;
        
        return mapToDto(analysis, estimatedScore);
    }

    private Severity determineSeverity(int score) {
        if (score >= 80) return Severity.CRITICAL;
        if (score >= 50) return Severity.HIGH;
        if (score >= 20) return Severity.MEDIUM;
        if (score > 0) return Severity.LOW;
        return Severity.NONE;
    }

    private EventLogAnalysisResponseDto mapToDto(EventLogAnalysis entity, int score) {
        EventLogAnalysisResponseDto dto = new EventLogAnalysisResponseDto();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setSeverity(entity.getSeverity());
        dto.setCreatedAt(entity.getCreatedAt());
        
        dto.setLogSource(entity.getLogSource());
        dto.setTotalEvents(entity.getTotalEvents());
        dto.setThreatsDetected(entity.getThreatsDetected());
        dto.setScore(score);
        
        if (entity.getFindings() != null && !entity.getFindings().isEmpty()) {
            dto.setFindings(Arrays.asList(entity.getFindings().split("\\|\\|")));
        } else {
            dto.setFindings(List.of());
        }
        
        return dto;
    }
}
