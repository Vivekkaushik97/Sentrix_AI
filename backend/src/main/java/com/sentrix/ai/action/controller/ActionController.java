package com.sentrix.ai.action.controller;

import com.sentrix.ai.action.domain.SecurityAction;
import com.sentrix.ai.action.dto.ActionRequestDto;
import com.sentrix.ai.action.dto.ApprovalRequestDto;
import com.sentrix.ai.action.service.ActionService;
import com.sentrix.ai.action.service.ApprovalGate;
import com.sentrix.ai.action.repository.SecurityActionRepository;
import com.sentrix.ai.action.service.ActionAuditService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/actions")
public class ActionController {

    private final ActionService actionService;
    private final ApprovalGate approvalGate;
    private final SecurityActionRepository actionRepository;
    private final ActionAuditService auditService;

    public ActionController(ActionService actionService, ApprovalGate approvalGate, 
                            SecurityActionRepository actionRepository, ActionAuditService auditService) {
        this.actionService = actionService;
        this.approvalGate = approvalGate;
        this.actionRepository = actionRepository;
        this.auditService = auditService;
    }

    @PostMapping
    public ResponseEntity<?> createAction(@Valid @RequestBody ActionRequestDto dto) {
        SecurityAction action = new SecurityAction();
        action.setActionType(dto.getActionType());
        action.setTargetReference(dto.getTargetReference());
        action.setReason(dto.getReason());
        action.setInvestigationId(dto.getInvestigationId());
        action.setIncidentId(dto.getIncidentId());
        // For simulation, static actor
        SecurityAction created = actionService.createAction(action, "system");
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<?> listActions() {
        return ResponseEntity.ok(actionRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAction(@PathVariable UUID id) {
        return actionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/request-approval")
    public ResponseEntity<?> requestApproval(@PathVariable UUID id) {
        try {
            actionService.requestApproval(id, "system");
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approveAction(@PathVariable UUID id, @Valid @RequestBody ApprovalRequestDto dto) {
        try {
            approvalGate.approveAction(id, "analyst", dto.getReason());
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<?> rejectAction(@PathVariable UUID id, @Valid @RequestBody ApprovalRequestDto dto) {
        try {
            approvalGate.rejectAction(id, "analyst", dto.getReason());
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Rejection requires a reason")) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAction(@PathVariable UUID id, @Valid @RequestBody ApprovalRequestDto dto) {
        try {
            actionService.cancelAction(id, "analyst", dto.getReason());
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/execute")
    public ResponseEntity<?> executeAction(@PathVariable UUID id) {
        try {
            actionService.executeAction(id, "system");
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getMessage()); // Or not found if id is wrong
        }
    }

    @GetMapping("/{id}/audit")
    public ResponseEntity<?> getAudit(@PathVariable UUID id) {
        return ResponseEntity.ok(auditService.getAuditTrail(id));
    }
}
