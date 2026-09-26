ALTER TABLE investigations 
ADD COLUMN owner_id UUID REFERENCES security_users(id),
ADD COLUMN assigned_analyst_id UUID REFERENCES security_users(id);

CREATE TABLE investigation_collaborators (
    investigation_id UUID NOT NULL REFERENCES investigations(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES security_users(id) ON DELETE CASCADE,
    PRIMARY KEY (investigation_id, user_id)
);

ALTER TABLE security_incidents
ADD COLUMN owner_id UUID REFERENCES security_users(id),
ADD COLUMN assigned_analyst_id UUID REFERENCES security_users(id),
ADD COLUMN acknowledged_at TIMESTAMP WITH TIME ZONE,
ADD COLUMN resolved_by_id UUID REFERENCES security_users(id);
