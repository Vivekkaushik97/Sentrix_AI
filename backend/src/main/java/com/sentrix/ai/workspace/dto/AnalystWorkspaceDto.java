package com.sentrix.ai.workspace.dto;

import com.sentrix.ai.posture.dto.SecurityPostureDto;
import com.sentrix.ai.search.dto.SecuritySearchResultDto;

import java.util.ArrayList;
import java.util.List;

public class AnalystWorkspaceDto {
    private SecurityPostureDto postureSummary;
    private List<SecuritySearchResultDto> priorityAlerts = new ArrayList<>();
    private List<SecuritySearchResultDto> activeIncidents = new ArrayList<>();
    private List<SecuritySearchResultDto> openInvestigations = new ArrayList<>();
    private List<SecuritySearchResultDto> pendingActions = new ArrayList<>();
    
    public SecurityPostureDto getPostureSummary() { return postureSummary; }
    public void setPostureSummary(SecurityPostureDto postureSummary) { this.postureSummary = postureSummary; }
    
    public List<SecuritySearchResultDto> getPriorityAlerts() { return priorityAlerts; }
    public void setPriorityAlerts(List<SecuritySearchResultDto> priorityAlerts) { this.priorityAlerts = priorityAlerts; }
    
    public List<SecuritySearchResultDto> getActiveIncidents() { return activeIncidents; }
    public void setActiveIncidents(List<SecuritySearchResultDto> activeIncidents) { this.activeIncidents = activeIncidents; }
    
    public List<SecuritySearchResultDto> getOpenInvestigations() { return openInvestigations; }
    public void setOpenInvestigations(List<SecuritySearchResultDto> openInvestigations) { this.openInvestigations = openInvestigations; }
    
    public List<SecuritySearchResultDto> getPendingActions() { return pendingActions; }
    public void setPendingActions(List<SecuritySearchResultDto> pendingActions) { this.pendingActions = pendingActions; }
}
