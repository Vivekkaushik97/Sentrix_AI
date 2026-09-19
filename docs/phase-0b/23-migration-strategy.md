# Database Migration Strategy

## Recommendation: Flyway
To manage schema changes securely, **Flyway** is recommended over Hibernate auto-DDL (`hibernate.hbm2ddl.auto=update`).

## Workflow
1. SQL migration scripts (e.g., `V1__init_schema.sql`) are committed to version control.
2. On Spring Boot startup, Flyway checks the `flyway_schema_history` table.
3. If new scripts exist, Flyway applies them to Supabase PostgreSQL before Hibernate initializes.

**Why not Supabase Migrations?**
While Supabase CLI offers migrations, keeping Flyway within the Spring Boot application ensures the backend and database schema are always perfectly in sync during CI/CD deployments across environments.
