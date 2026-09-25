# Phase 6: Windows Event Architecture

## Overview
The Windows Event Intelligence layer provides ingestion, normalization, detection, correlation, and scoring of real Windows Event Logs without depending on opaque native Windows APIs within the core Spring Boot application.

## Pipeline
1. **Collector / Importer**: A client (e.g. PowerShell `Get-WinEvent` script or an agent) collects raw EVTX data and formats it into the standard Sentrix JSON ingestion contract.
2. **Normalizer**: The `WindowsEventNormalizer` translates raw payloads into a canonical `WindowsEvent` entity, extracting `eventId`, `providerName`, `level`, `task`, etc., while preserving raw payload context.
3. **Validation**: The REST controller validates payloads (timestamps, required fields, size).
4. **Persistence**: Validated events are stored in the `windows_events` PostgreSQL table.
5. **Rule Engine**: Evaluates the canonical events against a suite of `WindowsEventRule` implementations (e.g., `FailedAuthenticationRule`, `AuditLogClearRule`). Output is a set of `WindowsEventDetection` entities.
6. **Correlation**: The `CorrelationEngine` links discrete detections or raw events together based on shared indicators (Time window, user, computer). Output is a set of `WindowsEventCorrelation` entities.
7. **Risk Classification**: A deterministic, transparent risk scorer calculates a score (0-100) based on severity, frequency, and correlation depth.
8. **Dashboard / Timeline**: Frontend React views the data.
9. **AI Context**: Events and Detections are pushed to the `SecurityContextBuilder` for the Phase 5 Assistant to read.

## Isolation
The entire pipeline exists within the `com.sentrix.ai.windowsevent` module, fully decoupled from the Phase 3 generic `eventlog` module.
