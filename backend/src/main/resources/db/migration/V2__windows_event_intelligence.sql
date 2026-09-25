CREATE TABLE windows_events (
    id UUID PRIMARY KEY,
    timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    computer_name VARCHAR(255) NOT NULL,
    log_name VARCHAR(255) NOT NULL,
    provider_name VARCHAR(255),
    event_id INTEGER NOT NULL,
    level VARCHAR(50),
    task VARCHAR(255),
    opcode VARCHAR(255),
    keywords VARCHAR(255),
    system_user VARCHAR(255),
    process_id BIGINT,
    thread_id BIGINT,
    channel VARCHAR(255),
    message TEXT,
    raw_event TEXT,
    source VARCHAR(255),
    ingestion_timestamp TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE windows_event_analyses (
    id UUID PRIMARY KEY REFERENCES analyses(id) ON DELETE CASCADE,
    risk_score INTEGER NOT NULL,
    computer_name VARCHAR(255)
);

CREATE TABLE windows_event_detections (
    id UUID PRIMARY KEY,
    windows_event_id UUID NOT NULL REFERENCES windows_events(id) ON DELETE CASCADE,
    analysis_id UUID NOT NULL REFERENCES windows_event_analyses(id) ON DELETE CASCADE,
    rule_id VARCHAR(255) NOT NULL,
    severity VARCHAR(50) NOT NULL,
    reason TEXT NOT NULL,
    evidence TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE windows_event_correlations (
    id UUID PRIMARY KEY,
    analysis_id UUID NOT NULL REFERENCES windows_event_analyses(id) ON DELETE CASCADE,
    correlation_key VARCHAR(255) NOT NULL,
    explanation TEXT NOT NULL,
    severity VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE windows_event_correlation_events (
    correlation_id UUID NOT NULL REFERENCES windows_event_correlations(id) ON DELETE CASCADE,
    windows_event_id UUID NOT NULL REFERENCES windows_events(id) ON DELETE CASCADE,
    PRIMARY KEY (correlation_id, windows_event_id)
);

-- Indexes for performance
CREATE INDEX idx_windows_events_timestamp ON windows_events(timestamp);
CREATE INDEX idx_windows_events_event_id ON windows_events(event_id);
CREATE INDEX idx_windows_events_computer_name ON windows_events(computer_name);
