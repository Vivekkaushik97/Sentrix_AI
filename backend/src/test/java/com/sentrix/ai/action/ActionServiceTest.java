package com.sentrix.ai.action;

import com.sentrix.ai.action.domain.ActionStatus;
import com.sentrix.ai.action.domain.ActionType;
import com.sentrix.ai.action.domain.SecurityAction;
import com.sentrix.ai.action.executor.DryRunExecutor;
import com.sentrix.ai.action.executor.ExecutorRegistry;
import com.sentrix.ai.action.repository.ActionApprovalRepository;
import com.sentrix.ai.action.repository.ActionAuditEntryRepository;
import com.sentrix.ai.action.repository.ActionExecutionRepository;
import com.sentrix.ai.action.repository.SecurityActionRepository;
import com.sentrix.ai.action.service.ActionAuditService;
import com.sentrix.ai.action.service.ActionService;
import com.sentrix.ai.action.service.ApprovalGate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ActionServiceTest {

    private SecurityActionRepository actionRepository;
    private ActionAuditEntryRepository auditRepository;
    private ActionApprovalRepository approvalRepository;
    private ActionExecutionRepository executionRepository;
    private ActionAuditService auditService;
    private ApprovalGate approvalGate;
    private ExecutorRegistry executorRegistry;
    private ActionService actionService;

    @BeforeEach
    void setUp() {
        actionRepository = mock(SecurityActionRepository.class);
        auditRepository = mock(ActionAuditEntryRepository.class);
        approvalRepository = mock(ActionApprovalRepository.class);
        executionRepository = mock(ActionExecutionRepository.class);
        
        auditService = new ActionAuditService(auditRepository);
        approvalGate = new ApprovalGate(actionRepository, approvalRepository, auditService);
        
        DryRunExecutor dryRunExecutor = new DryRunExecutor();
        executorRegistry = new ExecutorRegistry(Collections.singletonList(dryRunExecutor));
        
        actionService = new ActionService(actionRepository, auditService, approvalGate, executorRegistry, executionRepository);
    }

    @Test
    void testCreateAction() {
        SecurityAction action = new SecurityAction();
        action.setActionType(ActionType.CREATE_INVESTIGATION);
        when(actionRepository.save(any())).thenReturn(action);

        SecurityAction created = actionService.createAction(action, "system");
        assertEquals(ActionStatus.PROPOSED, created.getStatus());
        verify(actionRepository).save(any());
    }

    @Test
    void testRequestApproval() {
        UUID id = UUID.randomUUID();
        SecurityAction action = new SecurityAction();
        action.setId(id);
        action.setStatus(ActionStatus.PROPOSED);
        when(actionRepository.findById(id)).thenReturn(Optional.of(action));
        when(actionRepository.save(any())).thenReturn(action);

        actionService.requestApproval(id, "system");
        assertEquals(ActionStatus.PENDING_APPROVAL, action.getStatus());
    }

    @Test
    void testApproveAction() {
        UUID id = UUID.randomUUID();
        SecurityAction action = new SecurityAction();
        action.setId(id);
        action.setStatus(ActionStatus.PENDING_APPROVAL);
        when(actionRepository.findById(id)).thenReturn(Optional.of(action));

        approvalGate.approveAction(id, "analyst", "Looks good");
        assertEquals(ActionStatus.APPROVED, action.getStatus());
    }

    @Test
    void testRejectAction() {
        UUID id = UUID.randomUUID();
        SecurityAction action = new SecurityAction();
        action.setId(id);
        action.setStatus(ActionStatus.PENDING_APPROVAL);
        when(actionRepository.findById(id)).thenReturn(Optional.of(action));

        approvalGate.rejectAction(id, "analyst", "Too risky");
        assertEquals(ActionStatus.REJECTED, action.getStatus());
    }

    @Test
    void testExecuteApprovedAction() {
        UUID id = UUID.randomUUID();
        SecurityAction action = new SecurityAction();
        action.setId(id);
        action.setStatus(ActionStatus.APPROVED);
        action.setActionType(ActionType.CREATE_INVESTIGATION);
        when(actionRepository.findById(id)).thenReturn(Optional.of(action));

        actionService.executeAction(id, "system");
        assertEquals(ActionStatus.COMPLETED, action.getStatus());
    }

    @Test
    void testExecuteUnapprovedActionThrows() {
        UUID id = UUID.randomUUID();
        SecurityAction action = new SecurityAction();
        action.setId(id);
        action.setStatus(ActionStatus.PENDING_APPROVAL);
        when(actionRepository.findById(id)).thenReturn(Optional.of(action));

        assertThrows(IllegalStateException.class, () -> actionService.executeAction(id, "system"));
    }
}
