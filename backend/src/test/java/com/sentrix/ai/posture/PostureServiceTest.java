package com.sentrix.ai.posture;

import com.sentrix.ai.posture.domain.SecurityPostureSnapshot;
import com.sentrix.ai.posture.dto.SecurityPostureDto;
import com.sentrix.ai.posture.repository.SecurityPostureSnapshotRepository;
import com.sentrix.ai.posture.service.PostureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PostureServiceTest {

    private SecurityPostureSnapshotRepository repository;
    private PostureService postureService;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(SecurityPostureSnapshotRepository.class);
        postureService = new PostureService(repository);
    }

    @Test
    void testGetLatestPosture_WhenEmpty() {
        when(repository.findFirstByOrderByCreatedAtDesc()).thenReturn(Optional.empty());

        SecurityPostureDto result = postureService.getLatestPosture();

        assertNotNull(result);
        assertEquals(BigDecimal.ZERO, result.getOverallScore());
        assertEquals(BigDecimal.ZERO, result.getFraudRisk());
    }

    @Test
    void testGetLatestPosture_WhenExists() {
        SecurityPostureSnapshot snapshot = new SecurityPostureSnapshot();
        snapshot.setId(UUID.randomUUID());
        snapshot.setOverallScore(new BigDecimal("75.50"));
        snapshot.setFraudRisk(new BigDecimal("20.00"));
        snapshot.setEndpointRisk(new BigDecimal("10.00"));
        snapshot.setVulnerabilityRisk(new BigDecimal("15.00"));
        snapshot.setIncidentRisk(new BigDecimal("5.00"));
        snapshot.setInvestigationRisk(new BigDecimal("25.00"));
        snapshot.setActionRisk(new BigDecimal("0.50"));
        snapshot.setCreatedAt(ZonedDateTime.now());

        when(repository.findFirstByOrderByCreatedAtDesc()).thenReturn(Optional.of(snapshot));

        SecurityPostureDto result = postureService.getLatestPosture();

        assertNotNull(result);
        assertEquals(new BigDecimal("75.50"), result.getOverallScore());
        assertEquals(new BigDecimal("20.00"), result.getFraudRisk());
    }

    @Test
    void testCalculateAndSavePosture() {
        when(repository.save(any(SecurityPostureSnapshot.class))).thenAnswer(invocation -> {
            SecurityPostureSnapshot arg = invocation.getArgument(0);
            arg.setId(UUID.randomUUID());
            arg.setCreatedAt(ZonedDateTime.now());
            return arg;
        });

        SecurityPostureDto result = postureService.calculateAndSavePosture();

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(BigDecimal.ZERO, result.getOverallScore()); // As deterministic calculation sets it to 0 for now.
    }
}
