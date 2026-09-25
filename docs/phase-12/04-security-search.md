# Unified Security Search

## Objective
Provide a unified search abstraction allowing analysts to query across the entire Sentrix AI ecosystem from a single entry point.

## Search Domains
- Investigations
- Incidents
- UPI Fraud Rules/Transactions
- Windows Security Events
- CVE Intelligence
- Security Actions
- Action Audit Logs

## Architecture
**Primary Engine**: PostgreSQL.
Elasticsearch/OpenSearch is **NOT** required at this stage. The volume and search access patterns are fully supported by PostgreSQL using standard indexing, JPA abstractions, and pagination limits.

## Search Strategy (Delegate and Merge)
Rather than a complex, highly coupled monolithic SQL `UNION` query across completely disparate schema domains, the `SecuritySearchService` implements a **Scatter-Gather** (Delegate and Merge) pattern.
1. The incoming `SearchQueryDto` is validated and bounded.
2. The service asynchronously or sequentially queries individual domain repositories (e.g., IncidentRepository, InvestigationRepository) for the given keyword.
3. The results are transformed into a normalized `SecuritySearchResultDto` containing:
   - `id`: Original entity ID.
   - `type`: The domain type (e.g., `INCIDENT`, `CVE`).
   - `title`: Human-readable title or summary.
   - `status`: Current lifecycle state.
   - `timestamp`: Creation or event time.
4. The merged list is sorted by timestamp descending, paginated, and returned to the client.

## Security Controls
- **Bounded Result Size**: Hard limit of 100 items per unified search query to prevent memory exhaustion and DoS.
- **SQL Injection Prevention**: Relies strictly on Spring Data JPA derived queries and typed Specifications. No string concatenation for queries.
- **Data Boundaries**: Search respects multi-tenancy or access controls if present (all queries are scoped properly).
