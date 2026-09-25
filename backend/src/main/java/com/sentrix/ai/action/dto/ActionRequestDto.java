package com.sentrix.ai.action.dto;

import com.sentrix.ai.action.domain.ActionType;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class ActionRequestDto {
    @NotNull
    private ActionType actionType;
    private String targetReference;
    private String reason;
    private UUID investigationId;
    private UUID incidentId;

    public ActionType getActionType() { return actionType; }
    public void setActionType(ActionType actionType) { this.actionType = actionType; }
    public String getTargetReference() { return targetReference; }
    public void setTargetReference(String targetReference) { this.targetReference = targetReference; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public UUID getInvestigationId() { return investigationId; }
    public void setInvestigationId(UUID investigationId) { this.investigationId = investigationId; }
    public UUID getIncidentId() { return incidentId; }
    public void setIncidentId(UUID incidentId) { this.incidentId = incidentId; }
}
