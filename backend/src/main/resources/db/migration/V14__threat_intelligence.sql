CREATE TABLE threat_intelligence_sources (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE,
    reliability_score INTEGER NOT NULL DEFAULT 50,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE threat_indicators (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    indicator_type VARCHAR(50) NOT NULL,
    value VARCHAR(1000) NOT NULL,
    normalized_value VARCHAR(1000) NOT NULL,
    confidence_score INTEGER NOT NULL DEFAULT 0,
    severity VARCHAR(50),
    first_seen TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    last_seen TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE (indicator_type, normalized_value)
);

CREATE TABLE threat_intelligence_observations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    indicator_id UUID NOT NULL REFERENCES threat_indicators(id) ON DELETE CASCADE,
    source_id UUID NOT NULL REFERENCES threat_intelligence_sources(id),
    raw_metadata JSONB,
    observed_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE threat_indicator_correlations (
    indicator_id UUID NOT NULL REFERENCES threat_indicators(id) ON DELETE CASCADE,
    entity_type VARCHAR(50) NOT NULL,
    entity_id VARCHAR(255) NOT NULL,
    correlated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (indicator_id, entity_type, entity_id)
);

CREATE INDEX idx_threat_indicators_normalized ON threat_indicators(normalized_value);
CREATE INDEX idx_threat_correlations_entity ON threat_indicator_correlations(entity_type, entity_id);
