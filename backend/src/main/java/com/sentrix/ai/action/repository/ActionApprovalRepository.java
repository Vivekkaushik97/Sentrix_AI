package com.sentrix.ai.action.repository;

import com.sentrix.ai.action.domain.ActionApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActionApprovalRepository extends JpaRepository<ActionApproval, UUID> {
    List<ActionApproval> findByActionId(UUID actionId);
}
