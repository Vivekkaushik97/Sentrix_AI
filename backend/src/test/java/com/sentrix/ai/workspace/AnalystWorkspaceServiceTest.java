package com.sentrix.ai.workspace;

import com.sentrix.ai.action.domain.ActionPriority;
import com.sentrix.ai.action.domain.ActionStatus;
import com.sentrix.ai.action.domain.ActionType;
import com.sentrix.ai.action.domain.SecurityAction;
import com.sentrix.ai.action.repository.SecurityActionRepository;
import com.sentrix.ai.posture.dto.SecurityPostureDto;
import com.sentrix.ai.posture.service.PostureService;
import com.sentrix.ai.workspace.dto.AnalystWorkspaceDto;
import com.sentrix.ai.workspace.service.AnalystWorkspaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class AnalystWorkspaceServiceTest {

    private PostureService postureService;
    private SecurityActionRepository actionRepository;
    private AnalystWorkspaceService workspaceService;

    @BeforeEach
    void setUp() {
        postureService = Mockito.mock(PostureService.class);
        actionRepository = Mockito.mock(SecurityActionRepository.class);
        workspaceService = new AnalystWorkspaceService(postureService, actionRepository);
    }

    @Test
    void testGetWorkspaceData_EmptyCollections() {
        SecurityPostureDto posture = new SecurityPostureDto();
        posture.setOverallScore(BigDecimal.ZERO);
        when(postureService.getLatestPosture()).thenReturn(posture);
        when(actionRepository.findAll()).thenReturn(List.of());

        AnalystWorkspaceDto dto = workspaceService.getWorkspaceData();

        assertNotNull(dto);
        assertEquals(BigDecimal.ZERO, dto.getPostureSummary().getOverallScore());
        assertEquals(0, dto.getPendingActions().size());
        assertEquals(0, dto.getActiveIncidents().size());
    }

    @Test
    void testGetWorkspaceData_WithActions() {
        SecurityPostureDto posture = new SecurityPostureDto();
        posture.setOverallScore(new BigDecimal("50.0"));
        when(postureService.getLatestPosture()).thenReturn(posture);

        SecurityAction action = new SecurityAction();
        action.setId(UUID.randomUUID());
        action.setStatus(ActionStatus.PROPOSED);
        action.setActionType(ActionType.REVIEW_TRANSACTION);
        action.setPriority(ActionPriority.CRITICAL);
        action.setCreatedAt(OffsetDateTime.now());

        when(actionRepository.findAll()).thenReturn(List.of(action));

        AnalystWorkspaceDto dto = workspaceService.getWorkspaceData();

        assertNotNull(dto);
        assertEquals(new BigDecimal("50.0"), dto.getPostureSummary().getOverallScore());
        assertEquals(1, dto.getPendingActions().size());
        assertEquals("PROPOSED", dto.getPendingActions().get(0).getStatus());
    }
}
