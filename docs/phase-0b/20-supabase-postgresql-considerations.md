# Supabase PostgreSQL Considerations

* **UUIDv4 Support**: Natively available without `uuid-ossp` by using PG13+ `gen_random_uuid()`.
* **pgvector**: Must be explicitly enabled via `CREATE EXTENSION vector`.
* **Connection Pooling**: Supabase provides PgBouncer/Supavisor. Spring Boot's HikariCP should be configured to work compatibly with the Supabase connection string.
* **Row Level Security (RLS)**: While Supabase shines with RLS for direct client-to-DB connections, Sentrix AI uses a Spring Boot backend. RLS is largely bypassed by the backend service role, relying instead on Spring Security and JPQL queries (`WHERE session_id = ?`) for isolation.
* **JSONB**: Supported flawlessly by Supabase, used efficiently in `fraud_transactions`.
