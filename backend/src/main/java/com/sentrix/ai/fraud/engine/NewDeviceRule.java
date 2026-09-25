package com.sentrix.ai.fraud.engine;

import com.sentrix.ai.fraud.dto.FraudAnalysisRequestDto;
import org.springframework.stereotype.Component;

@Component
public class NewDeviceRule implements FraudRule {
    @Override
    public RuleResult evaluate(FraudAnalysisRequestDto request) {
        if (request.getDeviceInfo() != null && request.getDeviceInfo().toLowerCase().contains("new device")) {
            return new RuleResult(true, "Transaction initiated from a previously unseen device context.", 25);
        }
        return new RuleResult(false, null, 0);
    }
}
