package com.sentrix.ai.windowsevent.engine;

import com.sentrix.ai.windowsevent.entity.WindowsEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class WindowsRuleEngine {

    private final List<WindowsEventRule> rules;

    public WindowsRuleEngine(List<WindowsEventRule> rules) {
        this.rules = rules;
    }

    public List<WindowsEventRule.RuleDetection> evaluateEvent(WindowsEvent event, List<WindowsEvent> contextHistory) {
        List<WindowsEventRule.RuleDetection> detections = new ArrayList<>();
        
        for (WindowsEventRule rule : rules) {
            if (rule.appliesTo(event)) {
                Optional<WindowsEventRule.RuleDetection> detection = rule.evaluate(event, contextHistory);
                detection.ifPresent(detections::add);
            }
        }
        
        return detections;
    }
}
