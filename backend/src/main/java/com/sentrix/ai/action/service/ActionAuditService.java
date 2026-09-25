package com.sentrix.ai.action.service;

import com.sentrix.ai.action.domain.ActionAuditEntry;
import com.sentrix.ai.action.repository.ActionAuditEntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.List;

@Service
public class ActionAuditService {

    private final ActionAuditEntryRepository auditRepository;

    public ActionAuditService(ActionAuditEntryRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordEvent(UUID actionId, String eventType, String actor, String reason) {
        ActionAuditEntry entry = new ActionAuditEntry();
        entry.setActionId(actionId);
        entry.setEventType(eventType);
        entry.setActor(actor);
        entry.setReason(reason);
        auditRepository.save(entry);
    }
    
    @Transactional(readOnly = true)
    public List<ActionAuditEntry> getAuditTrail(UUID actionId) {
        return auditRepository.findByActionIdOrderByCreatedAtAsc(actionId);
    }
}
