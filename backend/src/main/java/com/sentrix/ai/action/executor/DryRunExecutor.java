package com.sentrix.ai.action.executor;

import com.sentrix.ai.action.domain.SecurityAction;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;

@Component
public class DryRunExecutor implements ActionExecutor {

    @Override
    public boolean supports(SecurityAction action) {
        // As a safe default, dry run supports everything allowlisted
        return true;
    }

    @Override
    public ExecutionResult execute(SecurityAction action) {
        String summary = String.format(
            "SIMULATED execution of %s on target %s at %s. No destructive changes were made.",
            action.getActionType(),
            action.getTargetReference(),
            OffsetDateTime.now()
        );
        return ExecutionResult.success(summary);
    }
}
