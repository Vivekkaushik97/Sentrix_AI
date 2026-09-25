CREATE TABLE analyses (
    id UUID PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    severity VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    error_message TEXT
);

CREATE TABLE fraud_analyses (
    id UUID PRIMARY KEY REFERENCES analyses(id) ON DELETE CASCADE,
    transaction_id VARCHAR(100),
    amount DECIMAL(15, 2),
    currency VARCHAR(10),
    transaction_timestamp TIMESTAMP,
    sender_info VARCHAR(255),
    receiver_info VARCHAR(255),
    device_info VARCHAR(255),
    ip_address VARCHAR(45),
    location VARCHAR(255),
    payment_channel VARCHAR(100),
    risk_score INTEGER,
    findings TEXT,
    is_suspicious BOOLEAN
);

CREATE TABLE event_log_analyses (
    id UUID PRIMARY KEY REFERENCES analyses(id) ON DELETE CASCADE,
    log_source VARCHAR(255),
    total_events INTEGER DEFAULT 0,
    threats_detected INTEGER DEFAULT 0,
    raw_log TEXT,
    findings TEXT
);

CREATE TABLE cve_searches (
    id UUID PRIMARY KEY REFERENCES analyses(id) ON DELETE CASCADE,
    cve_id VARCHAR(50),
    keyword VARCHAR(255),
    description TEXT,
    cvss_score DECIMAL(4, 1),
    severity VARCHAR(50),
    published_date TIMESTAMP,
    raw_response JSONB,
    findings TEXT
);

CREATE TABLE reports (
    id UUID PRIMARY KEY,
    analysis_id UUID REFERENCES analyses(id) ON DELETE SET NULL,
    title VARCHAR(255) NOT NULL,
    report_type VARCHAR(50) NOT NULL,
    summary TEXT,
    findings TEXT,
    severity VARCHAR(50),
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
