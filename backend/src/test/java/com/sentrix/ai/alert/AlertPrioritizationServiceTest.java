package com.sentrix.ai.alert;

import com.sentrix.ai.alert.enums.AlertPriority;
import com.sentrix.ai.alert.service.AlertPrioritizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlertPrioritizationServiceTest {

    private AlertPrioritizationService service;

    @BeforeEach
    void setUp() {
        service = new AlertPrioritizationService();
    }

    @Test
    void testCalculatePriority_Critical() {
        AlertPriority priority = service.calculatePriority(
                "CRITICAL",
                new BigDecimal("85"),
                15,
                true,
                true
        );
        assertEquals(AlertPriority.CRITICAL, priority);
    }

    @Test
    void testCalculatePriority_High() {
        AlertPriority priority = service.calculatePriority(
                "HIGH",
                new BigDecimal("40"),
                5,
                false,
                true
        );
        assertEquals(AlertPriority.HIGH, priority);
    }

    @Test
    void testCalculatePriority_Informational() {
        AlertPriority priority = service.calculatePriority(
                "UNKNOWN",
                BigDecimal.ZERO,
                0,
                false,
                false
        );
        assertEquals(AlertPriority.INFORMATIONAL, priority);
    }
}
