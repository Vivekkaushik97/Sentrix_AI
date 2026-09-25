package com.sentrix.ai.action.service;

import com.sentrix.ai.action.domain.ActionExecution;
import com.sentrix.ai.action.domain.ActionStatus;
import com.sentrix.ai.action.domain.SecurityAction;
import com.sentrix.ai.action.executor.ActionExecutor;
import com.sentrix.ai.action.executor.ExecutionResult;
import com.sentrix.ai.action.executor.ExecutorRegistry;
import com.sentrix.ai.action.repository.ActionExecutionRepository;
import com.sentrix.ai.action.repository.SecurityActionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class ActionService {

    private final SecurityActionRepository actionRepository;
    private final ActionAuditService auditService;
    private final ApprovalGate approvalGate;
    private final ExecutorRegistry executorRegistry;
    private final ActionExecutionRepository executionRepository;

    public ActionService(SecurityActionRepository actionRepository, ActionAuditService auditService, 
                         ApprovalGate approvalGate, ExecutorRegistry executorRegistry, 
                         ActionExecutionRepository executionRepository) {
        this.actionRepository = actionRepository;
        this.auditService = auditService;
        this.approvalGate = approvalGate;
        this.executorRegistry = executorRegistry;
        this.executionRepository = executionRepository;
    }

    @Transactional
    public SecurityAction createAction(SecurityAction action, String creator) {
        action.setStatus(ActionStatus.PROPOSED);
        SecurityAction saved = actionRepository.save(action);
        auditService.recordEvent(saved.getId(), "ACTION_CREATED", creator, "Action proposed");
        return saved;
    }

    @Transactional
    public void requestApproval(UUID actionId, String requester) {
        SecurityAction action = actionRepository.findById(actionId)
                .orElseThrow(() -> new IllegalArgumentException("Action not found"));

        if (action.getStatus() != ActionStatus.PROPOSED) {
            throw new IllegalStateException("Only proposed actions can request approval");
        }

        action.setStatus(ActionStatus.PENDING_APPROVAL);
        actionRepository.save(action);
        auditService.recordEvent(actionId, "APPROVAL_REQUESTED", requester, "Requested approval");
    }

    @Transactional
    public void cancelAction(UUID actionId, String actor, String reason) {
        SecurityAction action = actionRepository.findById(actionId)
                .orElseThrow(() -> new IllegalArgumentException("Action not found"));

        if (action.getStatus() == ActionStatus.EXECUTING || action.getStatus() == ActionStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel action in current state");
        }

        action.setStatus(ActionStatus.CANCELLED);
        actionRepository.save(action);
        auditService.recordEvent(actionId, "CANCELLED", actor, reason);
    }

    @Transactional
    public void executeAction(UUID actionId, String actor) {
        SecurityAction action = actionRepository.findById(actionId)
                .orElseThrow(() -> new IllegalArgumentException("Action not found"));

        approvalGate.verifyApproval(action);

        ActionExecutor executor = executorRegistry.getExecutor(action)
                .orElseThrow(() -> new IllegalArgumentException("No executor supports this action type"));

        action.setStatus(ActionStatus.EXECUTING);
        actionRepository.save(action);
        
        ActionExecution execution = new ActionExecution();
        execution.setActionId(actionId);
        execution.setExecutionStatus("STARTED");
        executionRepository.save(execution);

        auditService.recordEvent(actionId, "EXECUTION_STARTED", actor, "Execution started");

        try {
            ExecutionResult result = executor.execute(action);
            if (result.isSuccessful()) {
                action.setStatus(ActionStatus.COMPLETED);
                execution.setExecutionStatus("COMPLETED");
                execution.setResultSummary(result.getResultSummary());
                auditService.recordEvent(actionId, "EXECUTION_COMPLETED", actor, "Execution completed successfully");
            } else {
                action.setStatus(ActionStatus.FAILED);
                execution.setExecutionStatus("FAILED");
                execution.setFailureReason(result.getFailureReason());
                auditService.recordEvent(actionId, "EXECUTION_FAILED", actor, "Execution failed: " + result.getFailureReason());
            }
        } catch (Exception e) {
            action.setStatus(ActionStatus.FAILED);
            execution.setExecutionStatus("FAILED");
            execution.setFailureReason(e.getMessage());
            auditService.recordEvent(actionId, "EXECUTION_FAILED", actor, "Execution exception: " + e.getMessage());
        }
        
        execution.setCompletedAt(OffsetDateTime.now());
        executionRepository.save(execution);
        actionRepository.save(action);
    }
}
