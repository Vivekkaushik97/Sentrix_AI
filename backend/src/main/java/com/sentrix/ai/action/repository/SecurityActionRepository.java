package com.sentrix.ai.action.repository;

import com.sentrix.ai.action.domain.SecurityAction;
import com.sentrix.ai.action.domain.ActionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SecurityActionRepository extends JpaRepository<SecurityAction, UUID> {
    List<SecurityAction> findByStatus(ActionStatus status);
    List<SecurityAction> findByInvestigationId(UUID investigationId);
    List<SecurityAction> findByIncidentId(UUID incidentId);
}
