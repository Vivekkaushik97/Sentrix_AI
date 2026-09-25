# Security Posture Engine

## Overview
The Security Posture Engine deterministically aggregates existing real security evidence into a unified view. It calculates a standardized posture metric across multiple risk dimensions.

## Dimensions Evaluated
- **FRAUD_RISK**: Extracted from active UPI fraud rules and flagged transactions.
- **ENDPOINT_RISK**: Extracted from Windows Event Intelligence critical alerts.
- **VULNERABILITY_RISK**: Derived from active CVE exposure.
- **INCIDENT_RISK**: Derived from open/critical security incidents.
- **INVESTIGATION_RISK**: Derived from the volume and severity of active investigations.
- **ACTION_RISK**: Derived from unapproved or pending critical security actions.

## Deterministic Rule
The overarching requirement is that AI **DOES NOT** calculate this score. The score is mathematically formulated based strictly on PostgreSQL aggregates.

## Persistence
Snapshots are persisted to `security_posture_snapshots` only on a scheduled basis (or manual refresh trigger) to preserve historical posture degradation/improvement over time without requiring exhaustive point-in-time timeline queries on every dashboard load.
