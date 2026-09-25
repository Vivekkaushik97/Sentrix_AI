package com.sentrix.ai.investigation.repository;

import com.sentrix.ai.investigation.entity.Investigation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvestigationRepository extends JpaRepository<Investigation, UUID> {
}
