# Constraints

## Key Constraints
* **Primary Keys**: Every table has a Primary Key, generally `UUID`.
* **Foreign Keys**: Enforced referential integrity. (e.g., `analysis_records.session_id` must exist in `anonymous_sessions.id`).

## Data Integrity Rules (Check Constraints)
* **Risk Score**: `analysis_records.risk_score` must enforce `CHECK (risk_score >= 0 AND risk_score <= 100)`.
* **Fraud Probability**: `fraud_analyses.fraud_probability` must enforce `CHECK (fraud_probability >= 0.0 AND fraud_probability <= 1.0)`.

## Unique Constraints
* `cve_records.id`: The CVE string is naturally unique (Primary Key).
* `fraud_analyses.analysis_record_id`: Enforces the 1:1 relationship (a core record can only have one fraud detail record).
