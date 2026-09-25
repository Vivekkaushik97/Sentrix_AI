# Phase 3: Fraud Analysis Module

This document outlines the implementation of the Fraud Analysis Engine in Step 4.

## Deterministic Rule Engine
The fraud module currently uses a deterministic, explainable rule engine. Each rule contributes a specific weight to the total risk score.

### Implemented Rules:
1. **Large Amount Rule**: Triggers on amounts > 50,000 (Score +40).
2. **Night Time Rule**: Triggers between 1 AM and 5 AM (Score +30).
3. **New Device Rule**: Triggers if context indicates a new/unseen device (Score +25).

## Risk Scoring
The engine aggregates scores (capped at 100) and maps them to Severity:
- 80-100: CRITICAL
- 50-79: HIGH (Marked as Suspicious)
- 25-49: MEDIUM
- 1-24: LOW

## API Endpoints
- **POST `/api/v1/fraud/analyze`**: Submit transaction data, synchronously evaluate risk rules, persist the analysis, and return the scored findings.
- **GET `/api/v1/fraud/{id}`**: Fetch an existing fraud analysis.
