package com.sentrix.ai.posture.repository;

import com.sentrix.ai.posture.domain.SecurityPostureSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SecurityPostureSnapshotRepository extends JpaRepository<SecurityPostureSnapshot, UUID> {
    Optional<SecurityPostureSnapshot> findFirstByOrderByCreatedAtDesc();
}
