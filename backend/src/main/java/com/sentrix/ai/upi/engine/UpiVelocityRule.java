package com.sentrix.ai.upi.engine;

import com.sentrix.ai.common.enums.Severity;
import com.sentrix.ai.upi.entity.UpiTransaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UpiVelocityRule implements UpiFraudRule {

    private static final String RULE_ID = "UPI-VELOCITY-01";
    private static final int BURST_THRESHOLD = 5;

    @Override
    public String getRuleId() {
        return RULE_ID;
    }

    @Override
    public String getDescription() {
        return "Detects high velocity of UPI transactions from the same VPA within a short time window.";
    }

    @Override
    public Optional<UpiRuleDetection> evaluate(UpiTransaction transaction, List<UpiTransaction> history) {
        if (history == null || history.isEmpty()) return Optional.empty();

        long recentTxCount = history.stream()
                .filter(tx -> tx.getPayerVpa().equals(transaction.getPayerVpa()))
                .count();

        if (recentTxCount >= BURST_THRESHOLD) {
            String reason = String.format("Detected %d transactions from VPA '%s' within the last 15 minutes.", 
                    recentTxCount + 1, transaction.getPayerVpa());
            String evidence = String.format("Transaction ID %s represents the %dth transaction in a rapid burst.", 
                    transaction.getTransactionId(), recentTxCount + 1);
            
            return Optional.of(new UpiRuleDetection(RULE_ID, Severity.HIGH, reason, evidence, 40));
        }

        return Optional.empty();
    }
}
