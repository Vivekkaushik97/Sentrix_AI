package com.sentrix.ai.fraud.engine;

import com.sentrix.ai.fraud.dto.FraudAnalysisRequestDto;
import org.springframework.stereotype.Component;
import java.time.LocalTime;

@Component
public class NightTimeRule implements FraudRule {
    @Override
    public RuleResult evaluate(FraudAnalysisRequestDto request) {
        if (request.getTransactionTimestamp() != null) {
            LocalTime time = request.getTransactionTimestamp().toLocalTime();
            // Flag if between 1 AM and 5 AM
            if (time.isAfter(LocalTime.of(1, 0)) && time.isBefore(LocalTime.of(5, 0))) {
                return new RuleResult(true, "Transaction occurred during high-risk late night hours (1 AM - 5 AM).", 30);
            }
        }
        return new RuleResult(false, null, 0);
    }
}
