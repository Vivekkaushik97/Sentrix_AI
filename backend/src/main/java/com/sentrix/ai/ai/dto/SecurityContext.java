package com.sentrix.ai.ai.dto;

import java.util.ArrayList;
import java.util.List;

public class SecurityContext {
    private String aggregatedText;
    private List<String> sources = new ArrayList<>();
    private boolean hasSufficientData;

    public SecurityContext(String aggregatedText, List<String> sources, boolean hasSufficientData) {
        this.aggregatedText = aggregatedText;
        this.sources = sources;
        this.hasSufficientData = hasSufficientData;
    }

    public String getAggregatedText() { return aggregatedText; }
    public List<String> getSources() { return sources; }
    public boolean isHasSufficientData() { return hasSufficientData; }
}
