# Phase 14: Incident Ownership

## Overview
Extended Incident Management to support explicit operational ownership and lifecycle tracking.

## Architecture
- **Schema Update (V11):**
  - `owner_id` & `assigned_analyst_id`: Maps the incident to specific identities.
  - `acknowledged_at`: Timestamp tracking when an analyst first acknowledged the incident, critical for SLA reporting.
  - `resolved_by_id`: Tracks the user who ultimately closed the incident, providing clear attribution.
- **Operations:**
  - Only authorized roles (`SECURITY_ANALYST`, `SECURITY_MANAGER`) can claim or acknowledge an incident.
  - Ownership transitions are logged as `incident_events` to provide a clear timeline of handoffs.
