package com.sentrix.ai.fraud.engine;

import com.sentrix.ai.fraud.dto.FraudAnalysisRequestDto;

public interface FraudRule {
    RuleResult evaluate(FraudAnalysisRequestDto request);
}
