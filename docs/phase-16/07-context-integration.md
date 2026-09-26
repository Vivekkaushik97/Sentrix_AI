# Phase 16: Security Context Integration

## Overview
Extended the Phase 10 SecurityContext graph to natively support Threat Intelligence indicators.

## Architecture
- Threat Indicators (`Indicator`) and Observations (`Observation`) are now top-level nodes in the deterministic context graph.
- When an analyst views an `Incident` that has a `threat_indicator_correlation`, the context graph edges connect the incident directly to the relevant `ThreatIndicator`, supplying the AI and the frontend with immediate, structured context.
- **Constraints:** Extended incrementally without replacing the existing engine.
