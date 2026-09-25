package com.sentrix.ai.context.service;

import com.sentrix.ai.context.dto.SecurityContextDto;
import com.sentrix.ai.context.dto.SecurityContextEdgeDto;
import com.sentrix.ai.context.dto.SecurityContextGraphDto;
import com.sentrix.ai.context.dto.SecurityContextNodeDto;
import com.sentrix.ai.investigation.dto.InvestigationTimelineDto;
import com.sentrix.ai.investigation.service.InvestigationService;
import com.sentrix.ai.investigation.entity.InvestigationCorrelation;
import com.sentrix.ai.investigation.repository.InvestigationCorrelationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ContextResolutionService {

    private final InvestigationCorrelationRepository correlationRepository;
    private final InvestigationService investigationService;

    public ContextResolutionService(InvestigationCorrelationRepository correlationRepository, InvestigationService investigationService) {
        this.correlationRepository = correlationRepository;
        this.investigationService = investigationService;
    }

    @Transactional(readOnly = true)
    public SecurityContextDto resolveContext(String sourceType, String sourceId) {
        SecurityContextDto context = new SecurityContextDto();
        context.setSource(sourceType);
        context.setEntityId(sourceId);
        
        // This is a minimal stub for context resolution based on explicit Investigation correlations.
        // In a fully integrated deterministic graph, we would query the repositories directly.
        // E.g., if sourceType is UPI, we look up UpiTransaction, check FraudAnalysis, etc.
        // To remain strictly deterministic without inventing fake relationships:
        
        SecurityContextGraphDto graph = new SecurityContextGraphDto();
        List<SecurityContextNodeDto> nodes = new ArrayList<>();
        List<SecurityContextEdgeDto> edges = new ArrayList<>();
        
        SecurityContextNodeDto rootNode = new SecurityContextNodeDto();
        rootNode.setId(sourceId);
        rootNode.setType(sourceType);
        rootNode.setLabel(sourceType + " " + sourceId.substring(0, Math.min(8, sourceId.length())));
        nodes.add(rootNode);

        if ("INVESTIGATION".equalsIgnoreCase(sourceType)) {
            UUID invId = UUID.fromString(sourceId);
            List<InvestigationCorrelation> correlations = correlationRepository.findByInvestigationId(invId);
            
            for (InvestigationCorrelation c : correlations) {
                // Node
                SecurityContextNodeDto relatedNode = new SecurityContextNodeDto();
                relatedNode.setId(c.getRelatedRecordId());
                relatedNode.setType(c.getRelatedRecordType());
                relatedNode.setLabel(c.getRelatedRecordType() + " Entity");
                nodes.add(relatedNode);
                
                // Edge
                SecurityContextEdgeDto edge = new SecurityContextEdgeDto();
                edge.setSourceId(c.getSourceRecordId());
                edge.setTargetId(c.getRelatedRecordId());
                edge.setRelationshipType(c.getCorrelationReason());
                edge.setProvenance("InvestigationCorrelation");
                edges.add(edge);
            }
            
            // Timeline
            List<InvestigationTimelineDto> timeline = investigationService.getTimeline(invId);
            context.setTimeline(timeline);
        } else {
            // For other sources, return an empty timeline gracefully.
            context.setTimeline(new ArrayList<>());
        }
        
        graph.setNodes(nodes);
        graph.setEdges(edges);
        context.setGraph(graph);
        
        List<String> provenance = new ArrayList<>();
        provenance.add("Database Source Truth");
        context.setProvenance(provenance);
        
        return context;
    }
}
