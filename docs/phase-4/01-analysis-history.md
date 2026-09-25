# Phase 4: Analysis History

This document details the Analysis History functionality built in Step 1.

## Architecture
- Exposes generic persisted `Analysis` records via the new `history` package.
- Utilizes the existing `AnalysisRepository` which queries the `analyses` table directly.
- The results are mapped to a lightweight `HistoryResponseDto` for the frontend.

## API Endpoints
- **GET `/api/v1/history`**
  - Parameters: `page` (default: 0), `size` (default: 10).
  - Returns a paginated list of all analysis types (Fraud, Event Log, CVE) sorted by `createdAt` in descending order.

## Testing
- `HistoryControllerIntegrationTest` verifies the controller maps pagination arguments correctly and returns the expected structured JSON response using `MockMvc`.
