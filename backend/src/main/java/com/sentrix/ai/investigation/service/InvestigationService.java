package com.sentrix.ai.investigation.service;

import com.sentrix.ai.investigation.dto.InvestigationCreateDto;
import com.sentrix.ai.investigation.dto.InvestigationEventDto;
import com.sentrix.ai.investigation.entity.Investigation;
import com.sentrix.ai.investigation.entity.InvestigationEvent;
import com.sentrix.ai.investigation.repository.InvestigationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class InvestigationService {

    private final InvestigationRepository repository;

    public InvestigationService(InvestigationRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Investigation createInvestigation(InvestigationCreateDto dto) {
        Investigation inv = new Investigation();
        inv.setTitle(dto.getTitle());
        inv.setDescription(dto.getDescription());
        inv.setPriority(dto.getPriority() != null ? dto.getPriority() : "MEDIUM");
        inv.setOwner(dto.getOwner());
        return repository.save(inv);
    }

    @Transactional
    public Investigation addEvent(UUID investigationId, InvestigationEventDto eventDto) {
        Investigation inv = repository.findById(investigationId)
                .orElseThrow(() -> new IllegalArgumentException("Investigation not found"));
                
        InvestigationEvent event = new InvestigationEvent();
        event.setInvestigation(inv);
        event.setEventType(eventDto.getEventType());
        event.setReferenceId(eventDto.getReferenceId());
        event.setSummary(eventDto.getSummary());
        
        inv.getEvents().add(event);
        return repository.save(inv);
    }
    
    @Transactional
    public Investigation closeInvestigation(UUID investigationId) {
        Investigation inv = repository.findById(investigationId)
                .orElseThrow(() -> new IllegalArgumentException("Investigation not found"));
        inv.setStatus("CLOSED");
        inv.setClosedAt(OffsetDateTime.now());
        return repository.save(inv);
    }

    @Transactional
    public Investigation updateInvestigation(UUID investigationId, com.sentrix.ai.investigation.dto.InvestigationUpdateDto dto) {
        Investigation inv = repository.findById(investigationId)
                .orElseThrow(() -> new IllegalArgumentException("Investigation not found"));
        if (dto.getStatus() != null) {
            inv.setStatus(dto.getStatus());
            if ("CLOSED".equalsIgnoreCase(dto.getStatus()) || "RESOLVED".equalsIgnoreCase(dto.getStatus())) {
                inv.setClosedAt(OffsetDateTime.now());
            }
        }
        if (dto.getPriority() != null) {
            inv.setPriority(dto.getPriority());
        }
        return repository.save(inv);
    }

    @Transactional
    public Investigation addCorrelation(UUID investigationId, com.sentrix.ai.investigation.dto.InvestigationCorrelationDto dto) {
        Investigation inv = repository.findById(investigationId)
                .orElseThrow(() -> new IllegalArgumentException("Investigation not found"));
                
        com.sentrix.ai.investigation.entity.InvestigationCorrelation correlation = new com.sentrix.ai.investigation.entity.InvestigationCorrelation();
        correlation.setInvestigation(inv);
        correlation.setSourceRecordType(dto.getSourceRecordType());
        correlation.setSourceRecordId(dto.getSourceRecordId());
        correlation.setRelatedRecordType(dto.getRelatedRecordType());
        correlation.setRelatedRecordId(dto.getRelatedRecordId());
        correlation.setCorrelationReason(dto.getCorrelationReason());
        correlation.setConfidenceScore(dto.getConfidenceScore() != null ? dto.getConfidenceScore() : 50);
        
        if (inv.getCorrelations() == null) {
            inv.setCorrelations(new java.util.ArrayList<>());
        }
        inv.getCorrelations().add(correlation);
        return repository.save(inv);
    }

    @Transactional(readOnly = true)
    public java.util.List<com.sentrix.ai.investigation.dto.InvestigationTimelineDto> getTimeline(UUID investigationId) {
        Investigation inv = repository.findById(investigationId)
                .orElseThrow(() -> new IllegalArgumentException("Investigation not found"));
                
        java.util.List<com.sentrix.ai.investigation.dto.InvestigationTimelineDto> timeline = new java.util.ArrayList<>();
        
        if (inv.getEvents() != null) {
            for (InvestigationEvent e : inv.getEvents()) {
                com.sentrix.ai.investigation.dto.InvestigationTimelineDto dto = new com.sentrix.ai.investigation.dto.InvestigationTimelineDto();
                dto.setId(e.getId().toString());
                dto.setTimestamp(e.getCreatedAt());
                dto.setSource("INVESTIGATION_EVENT");
                dto.setEventType(e.getEventType());
                dto.setSummary(e.getSummary());
                dto.setRelatedEntityId(e.getReferenceId());
                timeline.add(dto);
            }
        }
        
        if (inv.getCorrelations() != null) {
            for (com.sentrix.ai.investigation.entity.InvestigationCorrelation c : inv.getCorrelations()) {
                com.sentrix.ai.investigation.dto.InvestigationTimelineDto dto = new com.sentrix.ai.investigation.dto.InvestigationTimelineDto();
                dto.setId(c.getId().toString());
                dto.setTimestamp(c.getCreatedAt());
                dto.setSource("CORRELATION");
                dto.setEventType("CROSS_SOURCE_CORRELATION");
                dto.setSummary(c.getCorrelationReason());
                dto.setRelatedEntityId(c.getSourceRecordId() + " -> " + c.getRelatedRecordId());
                timeline.add(dto);
            }
        }
        
        timeline.sort((t1, t2) -> t1.getTimestamp().compareTo(t2.getTimestamp()));
        return timeline;
    }
}
