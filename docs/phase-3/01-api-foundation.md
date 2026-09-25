# Phase 3: Backend API Foundation

This document outlines the API foundation built during Step 3 of Phase 3.

## Core API Structure
- The `Analysis` entity and subclasses map to PostgreSQL.
- Common components `ApiResponse` and `ApiErrorResponse` are used across all responses.
- `CorrelationIdFilter` assigns a unique `X-Correlation-Id` to all responses, accessible via `MDC`.
- Controllers are mapped under `/api/v1/*`.
- Standard Exception Handling is provided by `GlobalExceptionHandler`.

## Implemented Common Abstractions
- **Enums**: `AnalysisType`, `AnalysisStatus`, `Severity`.
- **Repository**: `AnalysisRepository` tracks metadata across all modules.
- **Exceptions**: Standardized domain exceptions like `ResourceNotFoundException`.
