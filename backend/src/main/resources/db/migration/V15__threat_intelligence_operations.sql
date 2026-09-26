CREATE TABLE threat_feeds (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    provider_name VARCHAR(255) NOT NULL,
    feed_name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ENABLED',
    last_sync_time TIMESTAMP WITH TIME ZONE,
    sync_interval_minutes INTEGER NOT NULL DEFAULT 60,
    confidence_override INTEGER,
    error_state TEXT,
    record_count INTEGER DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (provider_name, feed_name)
);

CREATE TABLE threat_campaigns (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    identifier VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    threat_classification VARCHAR(50) NOT NULL,
    confidence VARCHAR(50) NOT NULL DEFAULT 'UNKNOWN',
    severity VARCHAR(50) NOT NULL,
    related_ioc_count INTEGER DEFAULT 0,
    related_event_count INTEGER DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE threat_campaign_indicators (
    campaign_id UUID NOT NULL REFERENCES threat_campaigns(id) ON DELETE CASCADE,
    indicator_id UUID NOT NULL REFERENCES threat_indicators(id) ON DELETE CASCADE,
    association_reason VARCHAR(255) NOT NULL,
    associated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (campaign_id, indicator_id)
);

ALTER TABLE threat_indicators
    ADD COLUMN lifecycle_state VARCHAR(50) NOT NULL DEFAULT 'NEW',
    ADD COLUMN threat_classification VARCHAR(50) NOT NULL DEFAULT 'UNKNOWN';

CREATE INDEX idx_threat_indicators_state ON threat_indicators(lifecycle_state);
CREATE INDEX idx_threat_campaigns_class ON threat_campaigns(threat_classification);
