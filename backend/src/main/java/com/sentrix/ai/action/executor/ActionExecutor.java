package com.sentrix.ai.action.executor;

import com.sentrix.ai.action.domain.SecurityAction;

public interface ActionExecutor {
    boolean supports(SecurityAction action);
    ExecutionResult execute(SecurityAction action);
}
