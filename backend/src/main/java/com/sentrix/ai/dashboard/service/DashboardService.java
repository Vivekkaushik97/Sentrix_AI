package com.sentrix.ai.dashboard.service;

import com.sentrix.ai.common.enums.AnalysisType;
import com.sentrix.ai.common.enums.Severity;
import com.sentrix.ai.common.repository.AnalysisRepository;
import com.sentrix.ai.dashboard.dto.DashboardMetricsDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {

    private final AnalysisRepository analysisRepository;

    public DashboardService(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    @Transactional(readOnly = true)
    public DashboardMetricsDto getMetrics() {
        DashboardMetricsDto dto = new DashboardMetricsDto();
        dto.setTotalAnalyses(analysisRepository.count());
        dto.setHighRiskAnalyses(analysisRepository.countBySeverity(Severity.HIGH));
        dto.setFraudAnalyses(analysisRepository.countByType(AnalysisType.FRAUD));
        dto.setEventLogAnalyses(analysisRepository.countByType(AnalysisType.EVENT_LOG));
        dto.setCveAnalyses(analysisRepository.countByType(AnalysisType.CVE));
        return dto;
    }
}
