# Open Database Questions

The following specifics remain unresolved and must be finalized during their respective implementation phases.

1. **Exact fraud dataset features**
   * *Why it matters*: Dictates the exact structure of `fraud_transactions.features_json`.
   * *Phase*: Phase 5 (UPI Fraud Detection).
2. **Final embedding model**
   * *Why it matters*: Dictates the vector dimensions for `knowledge_chunks.embedding` (e.g., `vector(1536)` vs `vector(768)`).
   * *Phase*: Phase 9 (RAG).
3. **Exact CVE provider schema**
   * *Why it matters*: Determines if additional columns are needed in `cve_records` (e.g., CWE arrays).
   * *Phase*: Phase 7 (CVE Intelligence).
4. **Spring AI pgvector mapping**
   * *Why it matters*: Determines if `knowledge_chunks` should be managed via JPA entities or left purely to Spring AI's internal `JdbcTemplate`.
   * *Phase*: Phase 9 (RAG).
5. **Exact retention periods**
   * *Why it matters*: Required to program the pruning cron jobs for `anonymous_sessions`.
   * *Phase*: Phase 4 / Phase 17.
