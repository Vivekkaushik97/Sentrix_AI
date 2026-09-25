package com.sentrix.ai.fraud.engine;

import com.sentrix.ai.fraud.dto.FraudAnalysisRequestDto;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class LargeAmountRule implements FraudRule {
    private static final BigDecimal THRESHOLD = new BigDecimal("50000");

    @Override
    public RuleResult evaluate(FraudAnalysisRequestDto request) {
        if (request.getAmount() != null && request.getAmount().compareTo(THRESHOLD) > 0) {
            return new RuleResult(true, "Unusually large transaction amount exceeding typical limits.", 40);
        }
        return new RuleResult(false, null, 0);
    }
}
