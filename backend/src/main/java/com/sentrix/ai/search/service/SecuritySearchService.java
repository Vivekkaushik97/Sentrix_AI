package com.sentrix.ai.search.service;

import com.sentrix.ai.action.domain.SecurityAction;
import com.sentrix.ai.action.repository.SecurityActionRepository;
import com.sentrix.ai.search.dto.SearchQueryDto;
import com.sentrix.ai.search.dto.SecuritySearchResultDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecuritySearchService {

    private final SecurityActionRepository actionRepository;

    public SecuritySearchService(SecurityActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Transactional(readOnly = true)
    public List<SecuritySearchResultDto> search(SearchQueryDto queryDto) {
        List<SecuritySearchResultDto> results = new ArrayList<>();
        
        String query = queryDto.getQuery() != null ? queryDto.getQuery().toLowerCase() : "";
        String typeFilter = queryDto.getTypeFilter();

        // 1. Search Actions (from Phase 11)
        if (typeFilter == null || typeFilter.equalsIgnoreCase("ACTION")) {
            List<SecurityAction> actions = actionRepository.findAll();
            for (SecurityAction action : actions) {
                boolean matches = false;
                
                if (action.getId().toString().toLowerCase().contains(query)) matches = true;
                if (action.getActionType().name().toLowerCase().contains(query)) matches = true;
                if (action.getReason() != null && action.getReason().toLowerCase().contains(query)) matches = true;
                if (action.getTargetReference() != null && action.getTargetReference().toLowerCase().contains(query)) matches = true;
                
                if (matches) {
                    results.add(new SecuritySearchResultDto(
                            action.getId().toString(),
                            "ACTION",
                            action.getActionType().name(),
                            action.getStatus().name(),
                            action.getPriority().name(),
                            action.getCreatedAt()
                    ));
                }
            }
        }

        // 2. Extensible to Incidents, Investigations, CVEs, etc.
        // E.g., if (typeFilter == null || typeFilter.equalsIgnoreCase("INCIDENT")) { ... }

        // Sort descending by timestamp and limit
        return results.stream()
                .sorted(Comparator.comparing(SecuritySearchResultDto::getTimestamp).reversed())
                .limit(queryDto.getLimit())
                .collect(Collectors.toList());
    }
}
