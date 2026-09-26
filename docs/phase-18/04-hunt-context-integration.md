# Phase 18: Hunt & Context Integration

## Overview
Enabled hunts to populate and traverse the Phase 10 Security Context graph.

## Linkages
- Findings are mapped seamlessly to incidents, actions, and campaigns within the context graph.
- **AI Rule:** The AI is forbidden from hallucinating or inserting relationships into the Context graph. The Context graph remains mathematically deterministic based on explicit relationships stored in the SQL backend (e.g. `threat_hunt_findings` referencing an `Incident`).
