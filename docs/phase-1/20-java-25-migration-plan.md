# Java 25 LTS Migration Plan

## Context
- **Previous baseline:** Java 25 LTS
- **New baseline:** Java 25 LTS
- **Reason for migration:** Sentrix AI is still in foundation development, so changing the runtime baseline before Phase 2 minimizes migration cost and keeps the project on a current LTS Java release.

## Current Environment
- **Current Java version detected:** Java 25.0.1
- **Current Spring Boot version:** 3.2.4
- **Current Maven version:** 3.9.16
- **Current Maven compiler configuration:** `<java.version>21</java.version>`
- **Current CI Java configuration:** `java-version: '21'` in `.github/workflows/build.yml` using `actions/setup-java@v3`

## Files containing Java 25 LTS references
- `docs/phase-1/19-runtime-validation.md`
- `docs/phase-1/17-final-foundation-architecture.md`
- `docs/phase-1/02-project-structure.md`
- `docs/phase-1/01-repository-audit.md`
- `docs/phase-0/23-architecture-decisions.md`
- `docs/phase-0/22-development-roadmap.md`
- `docs/phase-0/03-system-architecture.md`
- `backend/pom.xml`
- `.github/workflows/build.yml`
- `README.md` (implied, will update)

## Dependency compatibility findings
- **Spring Boot 3.2.4:** Fails to compile with Java 25 (Unsupported class file major version 69 error from `spring-boot-maven-plugin`). 
- **Minimum compatible Spring Boot version:** Must upgrade to a newer Spring Boot version (e.g. 3.4.3) to support Java 25 compilation.
- **Other Dependencies:** Core Spring dependencies, Spring Security, Data JPA, Redis, AMQP, Actuator, OpenAPI, Flyway, PostgreSQL JDBC driver will implicitly upgrade to compatible versions managed by the new Spring Boot BOM. No breaking architectural changes are expected in upgrading from 3.2.4 to 3.4.3 for these foundation components.

## Planned changes
1. Upgrade Spring Boot parent version in `pom.xml` to `3.4.3` (or equivalent stable version supporting Java 25).
2. Change `<java.version>` in `pom.xml` to `25`.
3. Update `.github/workflows/build.yml` to use `java-version: '25'`.
4. Update all documentation files mentioning Java 25 LTS to reflect Java 25 LTS.
5. Create ADR `docs/phase-1/21-adr-java-25-baseline.md`.
6. Update root `README.md`.
7. Re-run complete Phase 1 validation (backend and frontend compilation/tests/runtime).

## Risks
- Upgrading Spring Boot might introduce minor configuration deprecations in `application.yml` or transitive dependency conflicts.
- Some dependencies (e.g., Springdoc) might require explicit version bumps to remain compatible with Spring Boot 3.4+.

## Validation strategy
- Run `mvn clean verify` to ensure successful compilation and tests.
- Run `docker compose up -d` to verify infrastructure.
- Note: Backend runtime validation will remain blocked because the local `.env` with Supabase PostgreSQL credentials is still missing, as discovered in the prior audit. However, frontend and backend build success will be fully validated.

## Rollback considerations
- Revert `pom.xml` to Spring Boot 3.2.4 and Java 25 LTS.
- Revert `.github/workflows/build.yml` to Java 25 LTS.
- Revert documentation changes.
