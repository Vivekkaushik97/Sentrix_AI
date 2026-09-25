# Phase 6: Ingestion API Contract

## Overview
Windows Event Logs are pushed to the backend via a structured JSON contract. The `WindowsEventIngestionRequest` validates the payload.

## Validation
- `source`: Required. Identifies the script/agent sending the data.
- `computerName`: Required. The host where the events originated.
- `events`: Array of `RawWindowsEventDto`. Maximum 1000 events per request to prevent Denial of Service and excessive memory consumption.
- `timestamp`: OffsetDateTime timezone-aware string (e.g. `2023-10-27T10:00:00Z`).

## Architecture Benefit
By forcing the ingestion to accept JSON, the Spring Boot application avoids depending on Windows-specific native APIs (`JNA` / `advapi32.dll`), allowing the backend to run on Linux servers flawlessly while analyzing data emitted by Windows endpoints.
