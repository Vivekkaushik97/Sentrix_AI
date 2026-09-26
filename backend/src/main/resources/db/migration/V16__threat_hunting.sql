CREATE TABLE threat_hunts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    state VARCHAR(50) NOT NULL DEFAULT 'DRAFT',
    priority VARCHAR(50) NOT NULL DEFAULT 'INFORMATIONAL',
    created_by_id UUID NOT NULL REFERENCES security_users(id),
    query_definition JSONB NOT NULL,
    started_at TIMESTAMP WITH TIME ZONE,
    completed_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE threat_hunt_findings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    hunt_id UUID NOT NULL REFERENCES threat_hunts(id) ON DELETE CASCADE,
    finding_type VARCHAR(100) NOT NULL,
    title VARCHAR(255) NOT NULL,
    explanation TEXT,
    severity VARCHAR(50) NOT NULL DEFAULT 'MEDIUM',
    confidence INTEGER NOT NULL DEFAULT 50,
    source_entity_type VARCHAR(100) NOT NULL,
    source_entity_id VARCHAR(255) NOT NULL,
    evidence_references JSONB,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE threat_hunt_notes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    hunt_id UUID NOT NULL REFERENCES threat_hunts(id) ON DELETE CASCADE,
    finding_id UUID REFERENCES threat_hunt_findings(id) ON DELETE CASCADE,
    author_id UUID NOT NULL REFERENCES security_users(id),
    content TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_threat_hunt_state ON threat_hunts(state);
CREATE INDEX idx_threat_hunt_findings_source ON threat_hunt_findings(source_entity_type, source_entity_id);
