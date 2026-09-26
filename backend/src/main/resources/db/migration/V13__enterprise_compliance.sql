CREATE TABLE compliance_frameworks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE,
    version VARCHAR(50) NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE compliance_controls (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    framework_id UUID NOT NULL REFERENCES compliance_frameworks(id) ON DELETE CASCADE,
    control_code VARCHAR(100) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    UNIQUE (framework_id, control_code)
);

CREATE TABLE evidence_records (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    evidence_type VARCHAR(100) NOT NULL,
    source_type VARCHAR(100) NOT NULL,
    source_id VARCHAR(255) NOT NULL,
    collector_id UUID REFERENCES security_users(id),
    metadata JSONB,
    collected_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE control_assessments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    control_id UUID NOT NULL REFERENCES compliance_controls(id) ON DELETE CASCADE,
    status VARCHAR(50) NOT NULL DEFAULT 'NOT_ASSESSED',
    assessed_by_id UUID REFERENCES security_users(id),
    assessed_at TIMESTAMP WITH TIME ZONE,
    justification TEXT
);

CREATE TABLE control_evidence_mapping (
    control_id UUID NOT NULL REFERENCES compliance_controls(id) ON DELETE CASCADE,
    evidence_id UUID NOT NULL REFERENCES evidence_records(id) ON DELETE CASCADE,
    mapped_by_id UUID REFERENCES security_users(id),
    mapped_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (control_id, evidence_id)
);

CREATE TABLE security_policies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    policy_name VARCHAR(255) NOT NULL UNIQUE,
    policy_value JSONB NOT NULL,
    updated_by_id UUID REFERENCES security_users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
