CREATE TABLE upi_transactions (
    id UUID PRIMARY KEY,
    transaction_id VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    currency VARCHAR(10) NOT NULL DEFAULT 'INR',
    payer_vpa VARCHAR(255) NOT NULL,
    payee_vpa VARCHAR(255) NOT NULL,
    device_id VARCHAR(255),
    ip_address VARCHAR(45),
    status VARCHAR(50) NOT NULL,
    risk_score INTEGER NOT NULL DEFAULT 0,
    severity VARCHAR(50) NOT NULL DEFAULT 'LOW',
    raw_metadata TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_upi_payer ON upi_transactions(payer_vpa);
CREATE INDEX idx_upi_timestamp ON upi_transactions(timestamp);
CREATE INDEX idx_upi_device ON upi_transactions(device_id);
