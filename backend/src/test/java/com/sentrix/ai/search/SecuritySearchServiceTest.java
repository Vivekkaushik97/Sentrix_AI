package com.sentrix.ai.search;

import com.sentrix.ai.action.domain.ActionPriority;
import com.sentrix.ai.action.domain.ActionStatus;
import com.sentrix.ai.action.domain.ActionType;
import com.sentrix.ai.action.domain.SecurityAction;
import com.sentrix.ai.action.repository.SecurityActionRepository;
import com.sentrix.ai.search.dto.SearchQueryDto;
import com.sentrix.ai.search.dto.SecuritySearchResultDto;
import com.sentrix.ai.search.service.SecuritySearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class SecuritySearchServiceTest {

    private SecurityActionRepository actionRepository;
    private SecuritySearchService searchService;

    @BeforeEach
    void setUp() {
        actionRepository = Mockito.mock(SecurityActionRepository.class);
        searchService = new SecuritySearchService(actionRepository);
    }

    @Test
    void testSearch_MatchesQuery() {
        SecurityAction action = new SecurityAction();
        action.setId(UUID.randomUUID());
        action.setActionType(ActionType.REVIEW_TRANSACTION);
        action.setStatus(ActionStatus.PROPOSED);
        action.setPriority(ActionPriority.HIGH);
        action.setReason("Suspicious login pattern detected.");
        action.setCreatedAt(OffsetDateTime.now());

        when(actionRepository.findAll()).thenReturn(List.of(action));

        SearchQueryDto query = new SearchQueryDto();
        query.setQuery("suspicious");

        List<SecuritySearchResultDto> results = searchService.search(query);

        assertEquals(1, results.size());
        assertEquals("ACTION", results.get(0).getType());
    }
}
