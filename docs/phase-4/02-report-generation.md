# Phase 4: Report Generation

This document outlines the structured Report Generation functionality implemented in Step 2.

## Domain Model
- `Report`: Entity mapping to the `reports` table in Supabase.
- Establishes a Many-to-One relationship with the `Analysis` root entity.
- Captures generated details such as `title`, `reportType`, `summary`, `findings`, `severity`, and `status`.

## API Endpoints
- **POST `/api/v1/reports`**
  - Payload: `ReportRequestDto` containing `analysisId` and an optional `title`.
  - Service queries the `Analysis` by ID.
  - Automatically derives the `reportType`, `severity`, and `findings` strictly from the existing persisted `Analysis` data (no fabrication).
  - Persists the new `Report` and returns `ReportResponseDto`.
  
- **GET `/api/v1/reports/{id}`**
  - Retrieves the generated report by its unique UUID.

## Persistence
- Schema uses the pre-existing `reports` table created in `V1__init_schema.sql`.
- No new Flyway migration was necessary.

## Testing
- `ReportControllerIntegrationTest` verifies the API endpoints and response formats utilizing `@WebMvcTest` with a mocked `ReportService`.
