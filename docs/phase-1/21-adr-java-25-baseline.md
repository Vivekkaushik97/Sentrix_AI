# Architecture Decision Record: Adopt Java 25 LTS as Sentrix AI Java Baseline

## Title: Adopt Java 25 LTS as Sentrix AI Java Baseline

## Status: Accepted

## Previous baseline: Java 21

## New baseline: Java 25 LTS

## Reason
Sentrix AI is still in foundation development, so changing the runtime baseline before Phase 2 minimizes migration cost and keeps the project on a current LTS Java release. 

## Scope
Foundation/runtime/build/CI only.

## Non-scope
No Phase 2/domain functionality.

## Context
The project was originally bootstrapped using Java 21 and Spring Boot 3.2.x. As Java 25 LTS becomes the new authoritative standard, migrating now prevents technical debt from accumulating during Phase 2 domain implementation.

## Alternatives considered
- **Staying on Java 21:** This would be safe in the short term, but would eventually require a more painful migration later when domain code heavily relies on Java 21 constructs or libraries that deprecate Java 21 support.

## Consequences
- Requires upgrading Spring Boot from 3.2.x to 3.4.3 to support Java 25 bytecode (major version 69).
- Requires updating the CI pipeline and local developer environments.

## Compatibility validation
- Spring Boot 3.4.3 has been tested and verified to compile against Java 25.
- The `actions/setup-java` in GitHub Actions natively supports Java 25.
- Maven 3.9.16 works correctly with Java 25.

## Migration impact
- Minimal code changes required.
- Configuration updates limited to `pom.xml`, `.github/workflows/build.yml`, and documentation files.
- The foundation (health endpoint, error handling, security configurations) remains functionally equivalent.
