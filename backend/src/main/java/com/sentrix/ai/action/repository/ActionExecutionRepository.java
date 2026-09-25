package com.sentrix.ai.action.repository;

import com.sentrix.ai.action.domain.ActionExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActionExecutionRepository extends JpaRepository<ActionExecution, UUID> {
    List<ActionExecution> findByActionId(UUID actionId);
}
