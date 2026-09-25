package com.sentrix.ai.eventlog.repository;

import com.sentrix.ai.eventlog.entity.EventLogAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventLogAnalysisRepository extends JpaRepository<EventLogAnalysis, UUID> {
}
