CREATE TABLE enterprise_audit_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    actor_id UUID REFERENCES security_users(id),
    actor_username VARCHAR(255),
    action VARCHAR(100) NOT NULL,
    resource_type VARCHAR(100) NOT NULL,
    resource_id VARCHAR(255),
    outcome VARCHAR(50) NOT NULL,
    correlation_id VARCHAR(255),
    timestamp TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    metadata JSONB
);

CREATE INDEX idx_audit_actor ON enterprise_audit_logs(actor_id);
CREATE INDEX idx_audit_timestamp ON enterprise_audit_logs(timestamp);
CREATE INDEX idx_audit_resource ON enterprise_audit_logs(resource_type, resource_id);
