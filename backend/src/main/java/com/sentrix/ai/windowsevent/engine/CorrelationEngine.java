package com.sentrix.ai.windowsevent.engine;

import com.sentrix.ai.common.enums.Severity;
import com.sentrix.ai.windowsevent.entity.WindowsEvent;
import com.sentrix.ai.windowsevent.entity.WindowsEventCorrelation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CorrelationEngine {

    // Simple correlation finding failed auth followed by success auth
    public List<WindowsEventCorrelation> correlate(List<WindowsEvent> eventBatch) {
        List<WindowsEventCorrelation> correlations = new ArrayList<>();
        
        // Very basic O(N^2) correlation for local batch context.
        // In a real big-data system, this would use a stream processor (Flink) or complex SQL.
        for (int i = 0; i < eventBatch.size(); i++) {
            WindowsEvent e1 = eventBatch.get(i);
            
            // Check for Failed Auth (4625)
            if (e1.getEventId() != null && e1.getEventId() == 4625 && e1.getUser() != null) {
                // Look ahead for a Success Auth (4624) by the same user within this batch
                for (int j = i + 1; j < eventBatch.size(); j++) {
                    WindowsEvent e2 = eventBatch.get(j);
                    if (e2.getEventId() != null && e2.getEventId() == 4624 && e1.getUser().equals(e2.getUser())) {
                        
                        WindowsEventCorrelation correlation = new WindowsEventCorrelation();
                        correlation.setCorrelationKey("AUTH_BRUTE_SUCCESS_" + e1.getUser());
                        correlation.setExplanation("Failed authentication followed by successful authentication for user: " + e1.getUser());
                        correlation.setSeverity(Severity.HIGH);
                        correlation.getEvents().add(e1);
                        correlation.getEvents().add(e2);
                        
                        correlations.add(correlation);
                        break; // Found the sequence, move to next
                    }
                }
            }
        }
        
        return correlations;
    }
}
