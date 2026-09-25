package com.sentrix.ai.workspace.service;

import com.sentrix.ai.action.domain.ActionStatus;
import com.sentrix.ai.action.repository.SecurityActionRepository;
import com.sentrix.ai.posture.service.PostureService;
import com.sentrix.ai.search.dto.SecuritySearchResultDto;
import com.sentrix.ai.workspace.dto.AnalystWorkspaceDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalystWorkspaceService {

    private final PostureService postureService;
    private final SecurityActionRepository actionRepository;
    // Inject IncidentRepository, InvestigationRepository when expanding

    public AnalystWorkspaceService(PostureService postureService, SecurityActionRepository actionRepository) {
        this.postureService = postureService;
        this.actionRepository = actionRepository;
    }

    @Transactional(readOnly = true)
    public AnalystWorkspaceDto getWorkspaceData() {
        AnalystWorkspaceDto dto = new AnalystWorkspaceDto();
        
        // 1. Posture Summary
        dto.setPostureSummary(postureService.getLatestPosture());

        // 2. Pending Actions (from Phase 11)
        List<SecuritySearchResultDto> pending = actionRepository.findAll().stream()
                .filter(a -> a.getStatus() == ActionStatus.PROPOSED)
                .map(a -> new SecuritySearchResultDto(
                        a.getId().toString(),
                        "ACTION",
                        a.getActionType().name(),
                        a.getStatus().name(),
                        a.getPriority().name(),
                        a.getCreatedAt()
                ))
                .collect(Collectors.toList());
        dto.setPendingActions(pending);

        // 3. (Mocked empty collections for active incidents/investigations since we are incrementally integrating them safely)
        // Kept purely data-driven, empty if no records exist in DB.
        
        return dto;
    }
}
