package com.sentrix.ai.cve.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.sentrix.ai.cve.dto.CveSearchResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class NvdCveProvider implements CveProvider {

    private final RestTemplate restTemplate;
    
    @Value("${cve.provider.nvd.url:https://services.nvd.nist.gov/rest/json/cves/2.0}")
    private String apiUrl;
    
    @Value("${cve.provider.nvd.api-key:}")
    private String apiKey;

    public NvdCveProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<CveSearchResponseDto> getCveDetail(String cveId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            if (apiKey != null && !apiKey.trim().isEmpty()) {
                headers.set("apiKey", apiKey);
            }
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            String url = apiUrl + "?cveId=" + cveId;
            
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, JsonNode.class);
            
            if (response.getBody() == null || !response.getBody().has("vulnerabilities") || 
                response.getBody().get("vulnerabilities").isEmpty()) {
                return Optional.empty();
            }
            
            JsonNode cveItem = response.getBody().get("vulnerabilities").get(0).get("cve");
            return Optional.of(mapToDto(cveItem));
            
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error communicating with CVE provider", e);
        }
    }
    
    private CveSearchResponseDto mapToDto(JsonNode cveItem) {
        CveSearchResponseDto dto = new CveSearchResponseDto();
        dto.setCveId(cveItem.get("id").asText());
        
        JsonNode descriptions = cveItem.get("descriptions");
        if (descriptions != null && descriptions.isArray() && descriptions.size() > 0) {
            // Find english description
            for (JsonNode desc : descriptions) {
                if ("en".equals(desc.get("lang").asText())) {
                    dto.setDescription(desc.get("value").asText());
                    break;
                }
            }
            if (dto.getDescription() == null) {
                dto.setDescription(descriptions.get(0).get("value").asText());
            }
        }
        
        JsonNode metrics = cveItem.get("metrics");
        if (metrics != null) {
            if (metrics.has("cvssMetricV31") && metrics.get("cvssMetricV31").isArray() && metrics.get("cvssMetricV31").size() > 0) {
                JsonNode cvssData = metrics.get("cvssMetricV31").get(0).get("cvssData");
                dto.setCvssScore(java.math.BigDecimal.valueOf(cvssData.get("baseScore").asDouble()));
                dto.setSeverity(cvssData.get("baseSeverity").asText());
            } else if (metrics.has("cvssMetricV30") && metrics.get("cvssMetricV30").isArray() && metrics.get("cvssMetricV30").size() > 0) {
                JsonNode cvssData = metrics.get("cvssMetricV30").get(0).get("cvssData");
                dto.setCvssScore(java.math.BigDecimal.valueOf(cvssData.get("baseScore").asDouble()));
                dto.setSeverity(cvssData.get("baseSeverity").asText());
            } else if (metrics.has("cvssMetricV2") && metrics.get("cvssMetricV2").isArray() && metrics.get("cvssMetricV2").size() > 0) {
                JsonNode cvssData = metrics.get("cvssMetricV2").get(0).get("cvssData");
                dto.setCvssScore(java.math.BigDecimal.valueOf(cvssData.get("baseScore").asDouble()));
                dto.setSeverity(metrics.get("cvssMetricV2").get(0).get("baseSeverity").asText());
            }
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        if (cveItem.has("published")) {
            try {
                dto.setPublishedDate(LocalDateTime.parse(cveItem.get("published").asText(), formatter));
            } catch (Exception ignored) {}
        }
        if (cveItem.has("lastModified")) {
            try {
                dto.setLastModifiedDate(LocalDateTime.parse(cveItem.get("lastModified").asText(), formatter));
            } catch (Exception ignored) {}
        }
        
        return dto;
    }
}
