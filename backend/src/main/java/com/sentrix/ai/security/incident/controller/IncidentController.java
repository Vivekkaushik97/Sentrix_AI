package com.sentrix.ai.security.incident.controller;

import com.sentrix.ai.security.incident.entity.SecurityIncident;
import com.sentrix.ai.security.incident.repository.SecurityIncidentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/incidents")
public class IncidentController {

    private final SecurityIncidentRepository repository;

    public IncidentController(SecurityIncidentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<Page<SecurityIncident>> getIncidents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        
        Page<SecurityIncident> incidents = repository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
                
        // Returning the entity directly. Spring Jackson will serialize it. 
        // Note: Beware of cyclic dependencies. If IncidentEvent links back to SecurityIncident,
        // it needs @JsonIgnore properties, which I should add in the entity.
        return ResponseEntity.ok(incidents);
    }
    
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<SecurityIncident> getIncident(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
