# Phase 1: Java 25 Migration Summary

## Before
- Java baseline: Java 21
- Spring Boot version: 3.2.4

## After
- Java baseline: Java 25 LTS
- Spring Boot version: 3.5.4 (Upgraded to resolve Java 25 bytecode compatibility)

## Explanation

**Why the migration was performed:**
To minimize migration cost by adopting the authoritative Java 25 LTS baseline before domain development (Phase 2) begins, avoiding technical debt.

**What files changed:**
- `backend/pom.xml`: Upgraded Spring Boot to 3.5.4, set `<java.version>25</java.version>`.
- `.github/workflows/build.yml`: Updated `actions/setup-java` to use JDK 25.

**What dependencies changed:**
- `spring-boot-starter-parent` from `3.2.4` to `3.4.3`. Transitive dependencies were updated accordingly by the BOM.

**What dependencies did NOT change:**
- Explicitly declared non-Spring versions (like Postgres driver or Flyway) were left to Spring's BOM, avoiding unnecessary arbitrary upgrades. Frontend dependencies (React, Vite, Tailwind, etc.) were left completely untouched as they remain standard.

**What documentation changed:**
- Replaced references to Java 21 with Java 25 LTS across `docs/phase-0/` and `docs/phase-1/` markdown files.
- Replaced references in the root `README.md`.
- Created ADR `docs/phase-1/21-adr-java-25-baseline.md`.
- Created Migration Plan `docs/phase-1/20-java-25-migration-plan.md`.
- Created Validation Report `docs/phase-1/22-java-25-validation-report.md`.

**What CI changed:**
- The GitHub Actions build workflow now configures JDK 25 (`java-version: '25'`) for the backend build step.

**Validation results:**
- Core compilation (`javac 25`) succeeds.
- Frontend build succeeds.
- Infrastructure (Docker, Redis, RabbitMQ) starts correctly.
- Phase 1 scope constraint (no Phase 2 domain logic) was preserved.

**Remaining blockers:**
1. **Supabase PostgreSQL Credentials:** The local `.env` file is missing, meaning actual backend database connectivity cannot be established, and the `spring-boot:run` goal continues to fail due to `Connection refused` on localhost.

*(Note: The `spring-boot-maven-plugin:repackage` failure regarding Java 25 major version 69 was resolved by upgrading the Spring Boot parent to `3.5.4`.)*
