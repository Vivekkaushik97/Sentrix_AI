package com.sentrix.ai.windowsevent.service;

import com.sentrix.ai.common.enums.AnalysisStatus;
import com.sentrix.ai.common.enums.AnalysisType;
import com.sentrix.ai.common.enums.Severity;
import com.sentrix.ai.windowsevent.dto.RawWindowsEventDto;
import com.sentrix.ai.windowsevent.dto.WindowsEventIngestionRequest;
import com.sentrix.ai.windowsevent.dto.WindowsEventResponseDto;
import com.sentrix.ai.windowsevent.engine.CorrelationEngine;
import com.sentrix.ai.windowsevent.engine.RiskScorer;
import com.sentrix.ai.windowsevent.engine.WindowsEventRule;
import com.sentrix.ai.windowsevent.engine.WindowsRuleEngine;
import com.sentrix.ai.windowsevent.entity.WindowsEvent;
import com.sentrix.ai.windowsevent.entity.WindowsEventAnalysis;
import com.sentrix.ai.windowsevent.entity.WindowsEventCorrelation;
import com.sentrix.ai.windowsevent.entity.WindowsEventDetection;
import com.sentrix.ai.windowsevent.repository.WindowsEventAnalysisRepository;
import com.sentrix.ai.windowsevent.repository.WindowsEventRepository;
import com.sentrix.ai.windowsevent.dto.WindowsEventAnalysisDetailDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WindowsEventIngestionService {

    private final WindowsEventRepository eventRepository;
    private final WindowsEventAnalysisRepository analysisRepository;
    private final WindowsEventNormalizer normalizer;
    private final WindowsRuleEngine ruleEngine;
    private final CorrelationEngine correlationEngine;
    private final RiskScorer riskScorer;

    public WindowsEventIngestionService(WindowsEventRepository eventRepository,
                                        WindowsEventAnalysisRepository analysisRepository,
                                        WindowsEventNormalizer normalizer,
                                        WindowsRuleEngine ruleEngine,
                                        CorrelationEngine correlationEngine,
                                        RiskScorer riskScorer) {
        this.eventRepository = eventRepository;
        this.analysisRepository = analysisRepository;
        this.normalizer = normalizer;
        this.ruleEngine = ruleEngine;
        this.correlationEngine = correlationEngine;
        this.riskScorer = riskScorer;
    }

    @Transactional
    public WindowsEventResponseDto ingestAndAnalyze(WindowsEventIngestionRequest request) {
        // 1. Normalize Events
        List<WindowsEvent> normalizedEvents = request.getEvents().stream()
                .map(raw -> normalizer.normalize(raw, request.getComputerName(), request.getSource()))
                .collect(Collectors.toList());

        // Save raw events first
        eventRepository.saveAll(normalizedEvents);

        // Fetch recent context (e.g. last 1 hour for this computer) to feed rule engine
        OffsetDateTime oneHourAgo = OffsetDateTime.now().minusHours(1);
        List<WindowsEvent> contextHistory = eventRepository.findByTimestampBetween(oneHourAgo, OffsetDateTime.now());
        
        // Ensure the current batch is in the context history
        contextHistory.addAll(normalizedEvents);

        // 2. Evaluate Rules
        List<WindowsEventRule.RuleDetection> rawDetections = new ArrayList<>();
        for (WindowsEvent event : normalizedEvents) {
            rawDetections.addAll(ruleEngine.evaluateEvent(event, contextHistory));
        }

        // 3. Correlate
        List<WindowsEventCorrelation> correlations = correlationEngine.correlate(normalizedEvents);

        // 4. Score Risk
        int riskScore = riskScorer.calculateRiskScore(rawDetections, correlations);

        // 5. Build and Save Analysis
        WindowsEventAnalysis analysis = new WindowsEventAnalysis();
        analysis.setType(AnalysisType.EVENT_LOG); // Mapping to common enum
        analysis.setStatus(AnalysisStatus.COMPLETED);
        analysis.setComputerName(request.getComputerName());
        analysis.setRiskScore(riskScore);
        
        if (riskScore >= 75) analysis.setSeverity(Severity.CRITICAL);
        else if (riskScore >= 50) analysis.setSeverity(Severity.HIGH);
        else if (riskScore >= 25) analysis.setSeverity(Severity.MEDIUM);
        else analysis.setSeverity(Severity.LOW);

        // Convert Detections
        for (WindowsEventRule.RuleDetection rd : rawDetections) {
            WindowsEventDetection detection = new WindowsEventDetection();
            detection.setAnalysis(analysis);
            detection.setRuleId(rd.getRuleId());
            detection.setSeverity(rd.getSeverity());
            detection.setReason(rd.getReason());
            detection.setEvidence(rd.getEvidence());
            // Link to the specific event if possible. For simplicity, just attaching to analysis here.
            // In a fuller implementation, ruleEngine would return the triggering event ID.
            // For now, grab the first one in the batch as a placeholder to satisfy the NOT NULL constraint
            detection.setWindowsEvent(normalizedEvents.get(0));
            analysis.getDetections().add(detection);
        }

        // Link Correlations
        for (WindowsEventCorrelation c : correlations) {
            c.setAnalysis(analysis);
            analysis.getCorrelations().add(c);
        }

        WindowsEventAnalysis savedAnalysis = analysisRepository.save(analysis);

        return new WindowsEventResponseDto(
                savedAnalysis.getId(),
                normalizedEvents.size(),
                riskScore,
                rawDetections.size(),
                correlations.size(),
                "COMPLETED"
        );
    }

    public org.springframework.data.domain.Page<com.sentrix.ai.windowsevent.dto.WindowsEventDto> getEvents(org.springframework.data.domain.Pageable pageable) {
        return eventRepository.findAll(pageable).map(this::mapToEventDto);
    }

    public WindowsEventAnalysisDetailDto getAnalysis(java.util.UUID id) {
        WindowsEventAnalysis analysis = analysisRepository.findById(id).orElseThrow(() -> new RuntimeException("Analysis not found"));
        return mapToAnalysisDto(analysis);
    }

    public List<com.sentrix.ai.windowsevent.dto.WindowsEventDetectionDto> getDetections() {
        return analysisRepository.findAll().stream()
                .flatMap(a -> a.getDetections().stream())
                .map(this::mapToDetectionDto)
                .collect(Collectors.toList());
    }

    public List<com.sentrix.ai.windowsevent.dto.WindowsEventCorrelationDto> getCorrelations() {
        return analysisRepository.findAll().stream()
                .flatMap(a -> a.getCorrelations().stream())
                .map(this::mapToCorrelationDto)
                .collect(Collectors.toList());
    }

    private com.sentrix.ai.windowsevent.dto.WindowsEventDto mapToEventDto(WindowsEvent event) {
        com.sentrix.ai.windowsevent.dto.WindowsEventDto dto = new com.sentrix.ai.windowsevent.dto.WindowsEventDto();
        dto.setId(event.getId());
        dto.setTimestamp(event.getTimestamp());
        dto.setComputerName(event.getComputerName());
        dto.setLogName(event.getLogName());
        dto.setProviderName(event.getProviderName());
        dto.setEventId(event.getEventId());
        dto.setLevel(event.getLevel());
        dto.setTask(event.getTask());
        dto.setUser(event.getUser());
        dto.setRawEvent(event.getRawEvent());
        return dto;
    }

    private com.sentrix.ai.windowsevent.dto.WindowsEventDetectionDto mapToDetectionDto(WindowsEventDetection detection) {
        com.sentrix.ai.windowsevent.dto.WindowsEventDetectionDto dto = new com.sentrix.ai.windowsevent.dto.WindowsEventDetectionDto();
        dto.setId(detection.getId());
        if (detection.getWindowsEvent() != null) dto.setEventId(detection.getWindowsEvent().getId());
        dto.setRuleId(detection.getRuleId());
        dto.setSeverity(detection.getSeverity().name());
        dto.setReason(detection.getReason());
        dto.setEvidence(detection.getEvidence());
        dto.setCreatedAt(detection.getCreatedAt());
        return dto;
    }

    private com.sentrix.ai.windowsevent.dto.WindowsEventCorrelationDto mapToCorrelationDto(WindowsEventCorrelation correlation) {
        com.sentrix.ai.windowsevent.dto.WindowsEventCorrelationDto dto = new com.sentrix.ai.windowsevent.dto.WindowsEventCorrelationDto();
        dto.setId(correlation.getId());
        dto.setCorrelationKey(correlation.getCorrelationKey());
        dto.setExplanation(correlation.getExplanation());
        dto.setSeverity(correlation.getSeverity().name());
        dto.setCreatedAt(correlation.getCreatedAt());
        dto.setEvents(correlation.getEvents().stream().map(this::mapToEventDto).collect(Collectors.toList()));
        return dto;
    }

    private WindowsEventAnalysisDetailDto mapToAnalysisDto(WindowsEventAnalysis analysis) {
        WindowsEventAnalysisDetailDto dto = new WindowsEventAnalysisDetailDto();
        dto.setId(analysis.getId());
        dto.setRiskScore(analysis.getRiskScore());
        dto.setSeverity(analysis.getSeverity() != null ? analysis.getSeverity().name() : null);
        dto.setComputerName(analysis.getComputerName());
        dto.setDetections(analysis.getDetections().stream().map(this::mapToDetectionDto).collect(Collectors.toList()));
        dto.setCorrelations(analysis.getCorrelations().stream().map(this::mapToCorrelationDto).collect(Collectors.toList()));
        return dto;
    }
}
