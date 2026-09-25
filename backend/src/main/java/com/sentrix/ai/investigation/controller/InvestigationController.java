package com.sentrix.ai.investigation.controller;

import com.sentrix.ai.common.ApiResponse;
import com.sentrix.ai.investigation.dto.InvestigationCreateDto;
import com.sentrix.ai.investigation.dto.InvestigationEventDto;
import com.sentrix.ai.investigation.entity.Investigation;
import com.sentrix.ai.investigation.repository.InvestigationRepository;
import com.sentrix.ai.investigation.service.InvestigationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/investigations")
public class InvestigationController {

    private final InvestigationService service;
    private final InvestigationRepository repository;

    public InvestigationController(InvestigationService service, InvestigationRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Investigation>> create(@RequestBody InvestigationCreateDto dto) {
        Investigation inv = service.createInvestigation(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(inv, "Investigation created", UUID.randomUUID().toString()));
    }

    @GetMapping
    public ResponseEntity<Page<Investigation>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(repository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Investigation> getById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/events")
    public ResponseEntity<ApiResponse<Investigation>> addEvent(@PathVariable UUID id, @RequestBody InvestigationEventDto eventDto) {
        Investigation inv = service.addEvent(id, eventDto);
        return ResponseEntity.ok(ApiResponse.success(inv, "Event added", UUID.randomUUID().toString()));
    }

    @PostMapping("/{id}/correlations")
    public ResponseEntity<ApiResponse<Investigation>> addCorrelation(@PathVariable UUID id, @RequestBody com.sentrix.ai.investigation.dto.InvestigationCorrelationDto dto) {
        Investigation inv = service.addCorrelation(id, dto);
        return ResponseEntity.ok(ApiResponse.success(inv, "Correlation added", UUID.randomUUID().toString()));
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<ApiResponse<Investigation>> close(@PathVariable UUID id) {
        Investigation inv = service.closeInvestigation(id);
        return ResponseEntity.ok(ApiResponse.success(inv, "Investigation closed", UUID.randomUUID().toString()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Investigation>> update(@PathVariable UUID id, @RequestBody com.sentrix.ai.investigation.dto.InvestigationUpdateDto dto) {
        Investigation inv = service.updateInvestigation(id, dto);
        return ResponseEntity.ok(ApiResponse.success(inv, "Investigation updated", UUID.randomUUID().toString()));
    }

    @GetMapping("/{id}/timeline")
    public ResponseEntity<java.util.List<com.sentrix.ai.investigation.dto.InvestigationTimelineDto>> getTimeline(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getTimeline(id));
    }
}
