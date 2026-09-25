package com.sentrix.ai.cve.service;

import com.sentrix.ai.common.exception.ResourceNotFoundException;
import com.sentrix.ai.cve.dto.CveSearchResponseDto;
import com.sentrix.ai.cve.provider.CveProvider;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CveIntelligenceService {

    private final CveProvider cveProvider;

    public CveIntelligenceService(CveProvider cveProvider) {
        this.cveProvider = cveProvider;
    }

    @Cacheable(value = "cve-detail", key = "#cveId", unless = "#result == null")
    public CveSearchResponseDto getCveDetail(String cveId) {
        return cveProvider.getCveDetail(cveId)
                .orElseThrow(() -> new ResourceNotFoundException("CVE not found: " + cveId));
    }
}
