package com.sentrix.ai.search.controller;

import com.sentrix.ai.search.dto.SearchQueryDto;
import com.sentrix.ai.search.dto.SecuritySearchResultDto;
import com.sentrix.ai.search.service.SecuritySearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
public class SecuritySearchController {

    private final SecuritySearchService searchService;

    public SecuritySearchController(SecuritySearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping
    public ResponseEntity<List<SecuritySearchResultDto>> search(@RequestBody SearchQueryDto query) {
        return ResponseEntity.ok(searchService.search(query));
    }
}
