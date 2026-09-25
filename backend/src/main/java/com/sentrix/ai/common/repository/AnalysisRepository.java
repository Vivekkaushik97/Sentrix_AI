package com.sentrix.ai.common.repository;

import com.sentrix.ai.common.entity.Analysis;
import com.sentrix.ai.common.enums.AnalysisType;
import com.sentrix.ai.common.enums.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, UUID> {
    long countBySeverity(Severity severity);
    long countByType(AnalysisType type);
}
