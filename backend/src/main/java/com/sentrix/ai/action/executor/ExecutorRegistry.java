package com.sentrix.ai.action.executor;

import com.sentrix.ai.action.domain.SecurityAction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ExecutorRegistry {

    private final List<ActionExecutor> executors;

    public ExecutorRegistry(List<ActionExecutor> executors) {
        this.executors = executors;
    }

    public Optional<ActionExecutor> getExecutor(SecurityAction action) {
        return executors.stream()
                .filter(e -> e.supports(action))
                .findFirst();
    }
}
