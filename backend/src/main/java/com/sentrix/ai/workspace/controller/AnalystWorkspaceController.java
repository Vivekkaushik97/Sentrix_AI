package com.sentrix.ai.workspace.controller;

import com.sentrix.ai.workspace.dto.AnalystWorkspaceDto;
import com.sentrix.ai.workspace.service.AnalystWorkspaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analyst/workspace")
public class AnalystWorkspaceController {

    private final AnalystWorkspaceService workspaceService;

    public AnalystWorkspaceController(AnalystWorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @GetMapping
    public ResponseEntity<AnalystWorkspaceDto> getWorkspaceData() {
        return ResponseEntity.ok(workspaceService.getWorkspaceData());
    }
}
