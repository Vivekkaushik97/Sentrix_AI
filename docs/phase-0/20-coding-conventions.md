# Coding & Naming Conventions

## Java (Spring Boot)
* **Packages**: `com.sentrix.ai.[module].[layer]` (e.g., `com.sentrix.ai.fraud.service`).
* **Classes**: PascalCase (e.g., `FraudDetectionService`).
* **Interfaces**: PascalCase, generally not prefixed with `I` (e.g., `EventLogParser`). Implementations suffixed with `Impl` (e.g., `EvtxEventLogParserImpl`).
* **Variables/Methods**: camelCase.
* **DTOs**: Suffixed with `Request` or `Response` (e.g., `FraudAnalysisRequest`).
* **Entities**: Singular, representing the domain concept (e.g., `AnalysisRecord`).

## Database (PostgreSQL)
* **Tables**: snake_case, plural (e.g., `analysis_records`).
* **Columns**: snake_case (e.g., `created_at`).
* **Primary Keys**: `id`.
* **Foreign Keys**: `[singular_table_name]_id` (e.g., `session_id`).

## REST API
* **URLs**: kebab-case, plural nouns (e.g., `/api/v1/event-logs`).
* **JSON Fields**: camelCase to match frontend conventions (e.g., `"fraudProbability": 0.85`).

## React (Frontend)
* **Components**: PascalCase, `.tsx` extension (e.g., `RiskScoreWidget.tsx`).
* **Hooks**: camelCase, prefixed with `use` (e.g., `useSession.ts`).
* **Services**: Axios API wrappers in camelCase (e.g., `cveService.ts`).
* **Types/Interfaces**: PascalCase, optionally prefixed with `I` or organized in `types/`.
