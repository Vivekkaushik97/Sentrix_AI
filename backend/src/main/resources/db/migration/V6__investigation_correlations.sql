CREATE TABLE investigation_correlations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    investigation_id UUID NOT NULL REFERENCES investigations(id) ON DELETE CASCADE,
    source_record_type VARCHAR(100) NOT NULL,
    source_record_id VARCHAR(255) NOT NULL,
    related_record_type VARCHAR(100) NOT NULL,
    related_record_id VARCHAR(255) NOT NULL,
    correlation_reason TEXT NOT NULL,
    confidence_score INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
