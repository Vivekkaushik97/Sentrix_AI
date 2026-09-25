package com.sentrix.ai.action.service;

import com.sentrix.ai.action.domain.ActionApproval;
import com.sentrix.ai.action.domain.ActionStatus;
import com.sentrix.ai.action.domain.SecurityAction;
import com.sentrix.ai.action.repository.ActionApprovalRepository;
import com.sentrix.ai.action.repository.SecurityActionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ApprovalGate {

    private final SecurityActionRepository actionRepository;
    private final ActionApprovalRepository approvalRepository;
    private final ActionAuditService auditService;

    public ApprovalGate(SecurityActionRepository actionRepository, ActionApprovalRepository approvalRepository, ActionAuditService auditService) {
        this.actionRepository = actionRepository;
        this.approvalRepository = approvalRepository;
        this.auditService = auditService;
    }

    @Transactional
    public void approveAction(UUID actionId, String approver, String reason) {
        SecurityAction action = actionRepository.findById(actionId)
                .orElseThrow(() -> new IllegalArgumentException("Action not found"));

        if (action.getStatus() != ActionStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Action is not pending approval");
        }

        action.setStatus(ActionStatus.APPROVED);
        actionRepository.save(action);

        ActionApproval approval = new ActionApproval();
        approval.setActionId(actionId);
        approval.setApproverIdentity(approver);
        approval.setDecision("APPROVED");
        approval.setReason(reason);
        approvalRepository.save(approval);

        auditService.recordEvent(actionId, "APPROVED", approver, reason);
    }

    @Transactional
    public void rejectAction(UUID actionId, String approver, String reason) {
        SecurityAction action = actionRepository.findById(actionId)
                .orElseThrow(() -> new IllegalArgumentException("Action not found"));

        if (action.getStatus() != ActionStatus.PENDING_APPROVAL && action.getStatus() != ActionStatus.PROPOSED) {
            throw new IllegalStateException("Action cannot be rejected in its current state");
        }

        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Rejection requires a reason");
        }

        action.setStatus(ActionStatus.REJECTED);
        actionRepository.save(action);

        ActionApproval approval = new ActionApproval();
        approval.setActionId(actionId);
        approval.setApproverIdentity(approver);
        approval.setDecision("REJECTED");
        approval.setReason(reason);
        approvalRepository.save(approval);

        auditService.recordEvent(actionId, "REJECTED", approver, reason);
    }

    public void verifyApproval(SecurityAction action) {
        if (action.getStatus() != ActionStatus.APPROVED) {
            throw new IllegalStateException("Action has not been approved");
        }
    }
}
