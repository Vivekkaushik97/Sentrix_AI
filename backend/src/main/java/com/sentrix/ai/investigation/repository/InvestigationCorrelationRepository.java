package com.sentrix.ai.investigation.repository;

import com.sentrix.ai.investigation.entity.InvestigationCorrelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvestigationCorrelationRepository extends JpaRepository<InvestigationCorrelation, UUID> {
    List<InvestigationCorrelation> findByInvestigationId(UUID investigationId);
}
