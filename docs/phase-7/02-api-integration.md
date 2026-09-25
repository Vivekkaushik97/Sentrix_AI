# Phase 7: API Integration

## Overview
The frontend strictly communicates with the backend REST endpoints created in Phase 6 (and extended in Phase 7 to support `GET` operations).

## Endpoints
- `POST /api/v1/windows-events/ingest`: Submits raw JSON from collectors.
- `GET /api/v1/windows-events`: Retrieves a paginated list of normalized events.
- `GET /api/v1/windows-events/detections`: Retrieves all triggered rule findings.
- `GET /api/v1/windows-events/correlations`: Retrieves all correlated event sequences.

## Client
- `src/api/windowsEvents.ts`: Centralized fetch wrappers handling JSON serialization and error throwing.
- `src/types/windowsEvents.ts`: Strict TypeScript interfaces mapping 1:1 with backend DTOs to ensure type safety.
