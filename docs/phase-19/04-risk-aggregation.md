# Phase 19: Risk Aggregation Engine

## Overview
Created a deterministic calculator to output a bounded risk score (0-100) for operational entities.

## Logic Parameters
- **Base Score:** Derived from the Incident or Investigation `severity` (e.g., HIGH = 70).
- **Multipliers:** 
  - Presence of a `CONFIRMED` threat campaign IOC: +20 points.
  - Active high-priority hunt findings linked to the incident: +10 points.
- **Ceiling:** The score is mathematically capped at 100.
- **AI Rule:** AI may describe the mathematical breakdown of this score but is restricted from calculating or updating the score autonomously.
