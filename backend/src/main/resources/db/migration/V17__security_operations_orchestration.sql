CREATE TABLE risk_aggregations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    entity_type VARCHAR(100) NOT NULL,
    entity_id VARCHAR(255) NOT NULL,
    aggregated_score INTEGER NOT NULL DEFAULT 0,
    calculation_reason JSONB,
    calculated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(entity_type, entity_id)
);

CREATE INDEX idx_risk_aggregations_score ON risk_aggregations(aggregated_score);
