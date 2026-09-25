package com.sentrix.ai.ai.service;

import com.sentrix.ai.ai.dto.SecurityContext;
import com.sentrix.ai.common.entity.Analysis;
import com.sentrix.ai.common.repository.AnalysisRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityContextBuilder {

    private final AnalysisRepository analysisRepository;

    public SecurityContextBuilder(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    public SecurityContext buildContext(String prompt) {
        List<String> sources = new ArrayList<>();
        StringBuilder contextText = new StringBuilder();
        
        // Fetch recent 5 analyses as baseline context
        Page<Analysis> recentAnalyses = analysisRepository.findAll(
                PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"))
        );
        
        if (recentAnalyses.isEmpty()) {
            return new SecurityContext("No security analyses found in the database.", sources, false);
        }
        
        contextText.append("Recent Security Events (Including Windows Events, Fraud, and CVE):\n");
        for (Analysis analysis : recentAnalyses) {
            String source = "Analysis ID: " + analysis.getId();
            sources.add(source);
            contextText.append("- [").append(analysis.getCreatedAt()).append("] ")
                    .append("Type: ").append(analysis.getType())
                    .append(", Severity: ").append(analysis.getSeverity())
                    .append("\n");
        }
        
        return new SecurityContext(contextText.toString(), sources, true);
    }
}
