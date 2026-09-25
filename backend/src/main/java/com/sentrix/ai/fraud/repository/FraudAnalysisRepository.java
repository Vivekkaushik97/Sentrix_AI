package com.sentrix.ai.fraud.repository;

import com.sentrix.ai.fraud.entity.FraudAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FraudAnalysisRepository extends JpaRepository<FraudAnalysis, UUID> {
}
