package com.sentrix.ai.posture.controller;

import com.sentrix.ai.posture.dto.SecurityPostureDto;
import com.sentrix.ai.posture.service.PostureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posture")
public class PostureController {

    private final PostureService postureService;

    public PostureController(PostureService postureService) {
        this.postureService = postureService;
    }

    @GetMapping
    public ResponseEntity<SecurityPostureDto> getLatestPosture() {
        return ResponseEntity.ok(postureService.getLatestPosture());
    }

    @PostMapping("/calculate")
    public ResponseEntity<SecurityPostureDto> triggerPostureCalculation() {
        // In a real environment, this might be triggered by a Cron job or event listener.
        // It's exposed here for deterministic testing and manual recalculation.
        return ResponseEntity.ok(postureService.calculateAndSavePosture());
    }
}
