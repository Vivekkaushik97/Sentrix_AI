# Pagination & Query Requirements

## Approach
* **Methodology**: Offset-based pagination via Spring Data `Pageable` (`LIMIT` and `OFFSET` in SQL).

## Targeted Queries
1. **Analysis History**:
   * Filter by `session_id`.
   * Sort by `created_at DESC` or `risk_score DESC`.
   * Paginated.
2. **Dashboard Data**:
   * Fetch latest 5 `analysis_records` for the `session_id`.
   * Aggregate counts `GROUP BY risk_classification`.
3. **Chat History**:
   * Fetch all `chat_messages` where `chat_session_id = ?` `ORDER BY created_at ASC`. (Typically unpaginated per session, assuming sessions are kept reasonably short).
