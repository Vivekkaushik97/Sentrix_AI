package com.sentrix.ai.history.service;

import com.sentrix.ai.common.entity.Analysis;
import com.sentrix.ai.common.repository.AnalysisRepository;
import com.sentrix.ai.history.dto.HistoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HistoryService {

    private final AnalysisRepository analysisRepository;

    public HistoryService(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    @Transactional(readOnly = true)
    public Page<HistoryResponseDto> getHistory(Pageable pageable) {
        return analysisRepository.findAll(pageable).map(this::mapToDto);
    }

    private HistoryResponseDto mapToDto(Analysis analysis) {
        HistoryResponseDto dto = new HistoryResponseDto();
        dto.setId(analysis.getId());
        dto.setType(analysis.getType());
        dto.setStatus(analysis.getStatus());
        dto.setSeverity(analysis.getSeverity());
        dto.setCreatedAt(analysis.getCreatedAt());
        dto.setErrorMessage(analysis.getErrorMessage());
        return dto;
    }
}
