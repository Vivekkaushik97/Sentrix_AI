package com.sentrix.ai.cve.provider;

import com.sentrix.ai.cve.dto.CveSearchResponseDto;
import java.util.Optional;

public interface CveProvider {
    
    /**
     * Retrieves detailed information about a specific CVE.
     * @param cveId The standard CVE identifier (e.g., "CVE-2021-44228")
     * @return CveSearchResponseDto containing mapped data, or empty if not found.
     */
    Optional<CveSearchResponseDto> getCveDetail(String cveId);
    
}
