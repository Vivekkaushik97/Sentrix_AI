package com.sentrix.ai.windowsevent.repository;

import com.sentrix.ai.windowsevent.entity.WindowsEventAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WindowsEventAnalysisRepository extends JpaRepository<WindowsEventAnalysis, UUID> {
}
