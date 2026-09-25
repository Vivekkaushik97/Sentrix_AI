package com.sentrix.ai.cve.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sentrix.ai.cve.dto.CveSearchResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

class NvdCveProviderTest {

    private RestTemplate restTemplate;
    private NvdCveProvider provider;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        provider = new NvdCveProvider(restTemplate);
        mapper = new ObjectMapper();
    }

    @Test
    void getCveDetail_ShouldReturnDto_WhenFound() {
        ObjectNode root = mapper.createObjectNode();
        ArrayNode vulns = mapper.createArrayNode();
        ObjectNode vulnNode = mapper.createObjectNode();
        ObjectNode cveNode = mapper.createObjectNode();
        
        cveNode.put("id", "CVE-2021-44228");
        ArrayNode descriptions = mapper.createArrayNode();
        ObjectNode desc = mapper.createObjectNode();
        desc.put("lang", "en");
        desc.put("value", "Log4j vulnerability");
        descriptions.add(desc);
        cveNode.set("descriptions", descriptions);
        
        vulnNode.set("cve", cveNode);
        vulns.add(vulnNode);
        root.set("vulnerabilities", vulns);
        
        ResponseEntity<com.fasterxml.jackson.databind.JsonNode> responseEntity = new ResponseEntity<>(root, HttpStatus.OK);
        
        Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(com.fasterxml.jackson.databind.JsonNode.class)))
                .thenReturn(responseEntity);
                
        Optional<CveSearchResponseDto> result = provider.getCveDetail("CVE-2021-44228");
        
        assertTrue(result.isPresent());
        assertEquals("CVE-2021-44228", result.get().getCveId());
        assertEquals("Log4j vulnerability", result.get().getDescription());
    }

    @Test
    void getCveDetail_ShouldReturnEmpty_WhenNotFound() {
        ObjectNode root = mapper.createObjectNode();
        root.set("vulnerabilities", mapper.createArrayNode());
        
        ResponseEntity<com.fasterxml.jackson.databind.JsonNode> responseEntity = new ResponseEntity<>(root, HttpStatus.OK);
        
        Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(com.fasterxml.jackson.databind.JsonNode.class)))
                .thenReturn(responseEntity);
                
        Optional<CveSearchResponseDto> result = provider.getCveDetail("CVE-9999-9999");
        
        assertFalse(result.isPresent());
    }
}
