package com.sentrix.ai.report.service;

import com.sentrix.ai.common.entity.Analysis;
import com.sentrix.ai.common.exception.ResourceNotFoundException;
import com.sentrix.ai.common.repository.AnalysisRepository;
import com.sentrix.ai.report.dto.ReportRequestDto;
import com.sentrix.ai.report.dto.ReportResponseDto;
import com.sentrix.ai.report.entity.Report;
import com.sentrix.ai.report.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final AnalysisRepository analysisRepository;

    public ReportService(ReportRepository reportRepository, AnalysisRepository analysisRepository) {
        this.reportRepository = reportRepository;
        this.analysisRepository = analysisRepository;
    }

    @Transactional
    public ReportResponseDto generateReport(ReportRequestDto request) {
        Analysis analysis = analysisRepository.findById(request.getAnalysisId())
                .orElseThrow(() -> new ResourceNotFoundException("Analysis not found with ID: " + request.getAnalysisId()));

        Report report = new Report();
        report.setAnalysis(analysis);
        report.setTitle(request.getTitle() != null ? request.getTitle() : analysis.getType() + " Security Report");
        report.setReportType(analysis.getType().name());
        report.setSeverity(analysis.getSeverity() != null ? analysis.getSeverity().name() : "NONE");
        report.setStatus("GENERATED");
        
        report.setSummary("Report generated successfully based on the analysis data.");
        report.setFindings("Findings from analysis: " + (analysis.getErrorMessage() == null ? "None" : analysis.getErrorMessage()));

        Report saved = reportRepository.save(report);
        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public ReportResponseDto getReport(UUID id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with ID: " + id));
        return mapToDto(report);
    }

    private ReportResponseDto mapToDto(Report report) {
        ReportResponseDto dto = new ReportResponseDto();
        dto.setId(report.getId());
        dto.setAnalysisId(report.getAnalysis().getId());
        dto.setTitle(report.getTitle());
        dto.setReportType(report.getReportType());
        dto.setSummary(report.getSummary());
        dto.setFindings(report.getFindings());
        dto.setSeverity(report.getSeverity());
        dto.setStatus(report.getStatus());
        dto.setCreatedAt(report.getCreatedAt());
        return dto;
    }
}
