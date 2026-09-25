package com.sentrix.ai.fraud.engine;

import com.sentrix.ai.fraud.dto.FraudAnalysisRequestDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FraudRulesTest {

    @Test
    void largeAmountRule_ShouldTrigger_WhenAmountExceedsThreshold() {
        LargeAmountRule rule = new LargeAmountRule();
        FraudAnalysisRequestDto request = new FraudAnalysisRequestDto();
        request.setAmount(new BigDecimal("50001"));
        
        RuleResult result = rule.evaluate(request);
        
        assertTrue(result.isTriggered());
        assertEquals(40, result.getScoreContribution());
    }

    @Test
    void largeAmountRule_ShouldNotTrigger_WhenAmountBelowThreshold() {
        LargeAmountRule rule = new LargeAmountRule();
        FraudAnalysisRequestDto request = new FraudAnalysisRequestDto();
        request.setAmount(new BigDecimal("1000"));
        
        RuleResult result = rule.evaluate(request);
        
        assertFalse(result.isTriggered());
    }

    @Test
    void nightTimeRule_ShouldTrigger_WhenBetween1And5AM() {
        NightTimeRule rule = new NightTimeRule();
        FraudAnalysisRequestDto request = new FraudAnalysisRequestDto();
        request.setTransactionTimestamp(LocalDateTime.of(2023, 10, 10, 3, 0));
        
        RuleResult result = rule.evaluate(request);
        
        assertTrue(result.isTriggered());
        assertEquals(30, result.getScoreContribution());
    }

    @Test
    void nightTimeRule_ShouldNotTrigger_WhenDuringDay() {
        NightTimeRule rule = new NightTimeRule();
        FraudAnalysisRequestDto request = new FraudAnalysisRequestDto();
        request.setTransactionTimestamp(LocalDateTime.of(2023, 10, 10, 14, 0));
        
        RuleResult result = rule.evaluate(request);
        
        assertFalse(result.isTriggered());
    }

    @Test
    void newDeviceRule_ShouldTrigger_WhenDeviceIsNew() {
        NewDeviceRule rule = new NewDeviceRule();
        FraudAnalysisRequestDto request = new FraudAnalysisRequestDto();
        request.setDeviceInfo("Browser on new device");
        
        RuleResult result = rule.evaluate(request);
        
        assertTrue(result.isTriggered());
        assertEquals(25, result.getScoreContribution());
    }

    @Test
    void fraudEngine_ShouldAggregateScores() {
        FraudEngine engine = new FraudEngine(java.util.List.of(
            new LargeAmountRule(),
            new NightTimeRule(),
            new NewDeviceRule()
        ));
        
        FraudAnalysisRequestDto request = new FraudAnalysisRequestDto();
        request.setAmount(new BigDecimal("100000"));
        request.setTransactionTimestamp(LocalDateTime.of(2023, 10, 10, 2, 0));
        request.setDeviceInfo("unknown new device");
        
        FraudEngine.FraudEvaluationResult result = engine.evaluate(request);
        
        assertEquals(95, result.getRiskScore());
        assertEquals(3, result.getFindings().size());
    }
}
