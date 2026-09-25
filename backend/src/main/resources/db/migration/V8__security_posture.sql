CREATE TABLE security_posture_snapshots (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    overall_score DECIMAL(5,2) NOT NULL,
    fraud_risk DECIMAL(5,2) NOT NULL,
    endpoint_risk DECIMAL(5,2) NOT NULL,
    vulnerability_risk DECIMAL(5,2) NOT NULL,
    incident_risk DECIMAL(5,2) NOT NULL,
    investigation_risk DECIMAL(5,2) NOT NULL,
    action_risk DECIMAL(5,2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_security_posture_snapshots_created_at ON security_posture_snapshots(created_at DESC);
