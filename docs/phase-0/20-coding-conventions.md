# Phase 0: Naming & Coding Conventions

This document defines the naming and architectural conventions for the Sentrix AI codebase to ensure consistency across the Java backend and React frontend.

## 1. Java / Spring Boot Conventions

### A. Packages
*   **Format**: `com.sentrix.[module].[layer]`
*   **Examples**:
    *   `com.sentrix.fraud.controller`
    *   `com.sentrix.fraud.service`
    *   `com.sentrix.fraud.entity`
    *   `com.sentrix.fraud.repository`
    *   `com.sentrix.fraud.dto`
    *   `com.sentrix.common.exception`

### B. Classes and Interfaces
*   **Format**: PascalCase
*   **Controllers**: Suffix with `Controller` (e.g., `FraudController`).
*   **Services**: Interface is `FraudService`, implementation is `FraudServiceImpl`.
*   **Repositories**: Suffix with `Repository` (e.g., `FraudAnalysisRepository`).
*   **Entities**: Name as singular nouns (e.g., `FraudAnalysis`, `CveRecord`).
*   **Data Transfer Objects (DTOs)**:
    *   Requests: `[Action]Request` (e.g., `FraudAnalyzeRequest`).
    *   Responses: `[Entity]Response` (e.g., `FraudAnalysisResponse`).

### C. Variables and Methods
*   **Format**: camelCase
*   **Methods**: Verbs indicating action (e.g., `calculateRiskScore()`, `findBySessionId()`).
*   **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_FILE_SIZE`).

## 2. Database Naming Conventions (PostgreSQL)

*   **Format**: snake_case for everything.
*   **Tables**: Plural nouns (e.g., `fraud_analyses`, `cve_records`).
*   **Primary Keys**: Simply `id` for surrogate UUID keys, or `[entity]_id` if referencing another table.
*   **Foreign Keys**: `[referenced_table_singular]_id` (e.g., `session_id`, `analysis_id`).
*   **Timestamps**: `created_at`, `updated_at`, `deleted_at`.
*   **Indexes**: `idx_[table]_[column]` (e.g., `idx_fraud_analyses_session_id`).
*   **Unique Constraints**: `uq_[table]_[column]` (e.g., `uq_users_email` if users existed).

## 3. REST API Naming Conventions

*   **Format**: kebab-case for paths, camelCase for JSON fields.
*   **URLs**: Use plural nouns representing resources.
    *   *Good*: `/api/v1/fraud-analyses`
    *   *Bad*: `/api/v1/getFraudAnalysis`
*   **Actions**: Use HTTP verbs correctly (GET for read, POST for create, PUT/PATCH for update, DELETE for remove).
    *   If an action doesn't fit a standard CRUD paradigm, use a sub-resource verb: `POST /api/v1/reports/{id}/generate`.

## 4. React / TypeScript Conventions

### A. Files and Folders
*   **Components**: PascalCase (e.g., `DashboardCard.tsx`).
*   **Hooks**: camelCase starting with `use` (e.g., `useSession.ts`).
*   **Utilities/Services**: camelCase (e.g., `apiClient.ts`, `formatters.ts`).
*   **Pages (Route components)**: Suffix with `Page` (e.g., `FraudHubPage.tsx`).

### B. Variables and Types
*   **Interfaces/Types**: PascalCase, often prefixed with `I` or `T` if needed to avoid component collision, but general PascalCase is preferred (e.g., `RiskProfile`, `FraudAnalysisResult`).
*   **Constants**: UPPER_SNAKE_CASE for global constants, camelCase for local consts.

### C. Component Structure
*   Prefer functional components with hooks.
*   Keep components small and focused. Extract complex logic into custom hooks.
*   Separate API fetching logic from presentation logic where possible.
