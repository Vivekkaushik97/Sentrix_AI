package com.sentrix.ai.investigation.service;

import com.sentrix.ai.investigation.dto.InvestigationCorrelationDto;
import com.sentrix.ai.investigation.dto.InvestigationCreateDto;
import com.sentrix.ai.investigation.dto.InvestigationEventDto;
import com.sentrix.ai.investigation.dto.InvestigationTimelineDto;
import com.sentrix.ai.investigation.entity.Investigation;
import com.sentrix.ai.investigation.repository.InvestigationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class InvestigationServiceTest {

    @Mock
    private InvestigationRepository repository;

    @InjectMocks
    private InvestigationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateInvestigation() {
        InvestigationCreateDto dto = new InvestigationCreateDto();
        dto.setTitle("Test");
        
        Investigation inv = new Investigation();
        inv.setId(UUID.randomUUID());
        inv.setTitle("Test");
        
        when(repository.save(any(Investigation.class))).thenReturn(inv);
        
        Investigation result = service.createInvestigation(dto);
        assertEquals("Test", result.getTitle());
    }

    @Test
    void testAddCorrelation() {
        UUID id = UUID.randomUUID();
        Investigation inv = new Investigation();
        inv.setId(id);
        
        InvestigationCorrelationDto dto = new InvestigationCorrelationDto();
        dto.setSourceRecordId("SRC_123");
        dto.setRelatedRecordId("REL_456");
        dto.setCorrelationReason("IP Match");
        
        when(repository.findById(id)).thenReturn(Optional.of(inv));
        when(repository.save(any(Investigation.class))).thenReturn(inv);
        
        Investigation result = service.addCorrelation(id, dto);
        assertNotNull(result.getCorrelations());
        assertEquals(1, result.getCorrelations().size());
        assertEquals("IP Match", result.getCorrelations().get(0).getCorrelationReason());
    }
    
    @Test
    void testGetTimelineEmpty() {
        UUID id = UUID.randomUUID();
        Investigation inv = new Investigation();
        inv.setId(id);
        
        when(repository.findById(id)).thenReturn(Optional.of(inv));
        
        List<InvestigationTimelineDto> timeline = service.getTimeline(id);
        assertTrue(timeline.isEmpty());
    }
}
