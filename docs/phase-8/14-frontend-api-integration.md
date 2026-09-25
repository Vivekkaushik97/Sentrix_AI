# Phase 8: Frontend API Integration

## Strategy
Created specific API client wrappers and robust TypeScript definition files to handle requests to `api/v1/upi`, `api/v1/incidents`, and `api/v1/live/events`.

## Type Safety
- Interfaces strictly map to the Java DTOs to avoid `undefined` errors during object hydration.
- Ensures the UI stays compliant with the exact schema produced by the backend IncidentEngine and UpiFraudEngine.
