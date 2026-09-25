# Phase 8: Security Event Correlation

## Overview
Phase 8 introduces a top-level `IncidentEngine` acting as a global `SecurityEventProcessor`. While individual modules (like Fraud or Windows) process their own data silos, the Incident Engine observes the raw `SecurityEvent` flow asynchronously off the RabbitMQ exchanges to correlate behaviors.

## Schema
- **`SecurityIncident`**: Represents the overarching investigation container (the case file).
- **`IncidentEvent`**: Represents an individual node in the timeline of the attack, linking back to the raw entity (UPI, Windows Event) via an opaque string `entity_reference_id` and enum `event_type`.

## Benefits
By listening uniformly to the normalized `SecurityEvent`, Sentrix can detect a high-risk Windows login failure instantly chained to an anomalous UPI transfer attempt without the Windows module needing to be explicitly aware of the UPI module, ensuring clean bounded contexts.
