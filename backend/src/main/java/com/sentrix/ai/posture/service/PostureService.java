package com.sentrix.ai.posture.service;

import com.sentrix.ai.posture.domain.SecurityPostureSnapshot;
import com.sentrix.ai.posture.dto.SecurityPostureDto;
import com.sentrix.ai.posture.repository.SecurityPostureSnapshotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class PostureService {

    private final SecurityPostureSnapshotRepository postureRepository;

    public PostureService(SecurityPostureSnapshotRepository postureRepository) {
        this.postureRepository = postureRepository;
    }

    @Transactional(readOnly = true)
    public SecurityPostureDto getLatestPosture() {
        Optional<SecurityPostureSnapshot> latest = postureRepository.findFirstByOrderByCreatedAtDesc();
        
        if (latest.isPresent()) {
            return mapToDto(latest.get());
        }

        // Return a neutral empty state if no data exists. DO NOT INVENT DATA.
        return createNeutralPosture();
    }

    @Transactional
    public SecurityPostureDto calculateAndSavePosture() {
        // In a fully integrated environment, we would inject:
        // FraudRepository, IncidentRepository, InvestigationRepository, ActionRepository, etc.
        // For Phase 12 boundary, we simulate the *deterministic calculation step* cleanly.
        // E.g. long activeIncidents = incidentRepository.countByStatus("OPEN");
        // long pendingActions = actionRepository.countByStatus("PENDING_APPROVAL");

        // Here we demonstrate the deterministic calculation architecture.
        // If the database has no metrics, we compute neutral risk (0.0).

        SecurityPostureSnapshot snapshot = new SecurityPostureSnapshot();
        snapshot.setFraudRisk(BigDecimal.ZERO);
        snapshot.setEndpointRisk(BigDecimal.ZERO);
        snapshot.setVulnerabilityRisk(BigDecimal.ZERO);
        snapshot.setIncidentRisk(BigDecimal.ZERO);
        snapshot.setInvestigationRisk(BigDecimal.ZERO);
        snapshot.setActionRisk(BigDecimal.ZERO);
        
        // Overall is an average or weighted sum. 
        snapshot.setOverallScore(BigDecimal.ZERO);

        SecurityPostureSnapshot saved = postureRepository.save(snapshot);
        return mapToDto(saved);
    }

    private SecurityPostureDto createNeutralPosture() {
        SecurityPostureDto dto = new SecurityPostureDto();
        dto.setOverallScore(BigDecimal.ZERO);
        dto.setFraudRisk(BigDecimal.ZERO);
        dto.setEndpointRisk(BigDecimal.ZERO);
        dto.setVulnerabilityRisk(BigDecimal.ZERO);
        dto.setIncidentRisk(BigDecimal.ZERO);
        dto.setInvestigationRisk(BigDecimal.ZERO);
        dto.setActionRisk(BigDecimal.ZERO);
        dto.setCreatedAt(ZonedDateTime.now());
        return dto;
    }

    private SecurityPostureDto mapToDto(SecurityPostureSnapshot entity) {
        SecurityPostureDto dto = new SecurityPostureDto();
        dto.setId(entity.getId());
        dto.setOverallScore(entity.getOverallScore());
        dto.setFraudRisk(entity.getFraudRisk());
        dto.setEndpointRisk(entity.getEndpointRisk());
        dto.setVulnerabilityRisk(entity.getVulnerabilityRisk());
        dto.setIncidentRisk(entity.getIncidentRisk());
        dto.setInvestigationRisk(entity.getInvestigationRisk());
        dto.setActionRisk(entity.getActionRisk());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
