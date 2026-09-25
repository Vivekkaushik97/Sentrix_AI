# Java 25 Packaging Diagnosis

## 1. Current Toolchain
- **Java**: 25.0.1 (LTS)
- **Maven**: 3.9.16
- **Spring Boot**: 3.4.3
- **Spring Framework (Transitive)**: 6.2.3
- **Maven Compiler Plugin**: Inherited from Spring Boot (3.14.0)

## 2. Exact Failure
During the `maven verify` / `maven package` phase, compilation succeeds, but packaging fails:
```
Failed to execute goal org.springframework.boot:spring-boot-maven-plugin:3.4.3:repackage
java.lang.IllegalArgumentException: Unsupported class file major version 69
```

## 3. Root Cause
The `spring-boot-maven-plugin:3.4.3` executes its `repackage` goal by reading compiled `.class` files to identify the main class. This reading relies on the Spring Framework's embedded ASM parser (`org.springframework.asm.ClassReader` inside `spring-core:6.2.3`). Spring Core 6.2.3 relies on ASM versions older than 9.8, which do not recognize Java 25 class files (major version 69).

## 4. Evidence
The execution of `mvnw clean package -X` revealed the following stack trace:
```
Caused by: java.lang.IllegalArgumentException: Unsupported class file major version 69
      at org.springframework.asm.ClassReader.<init> (ClassReader.java:200)
      at org.springframework.boot.loader.tools.MainClassFinder.createClassDescriptor (MainClassFinder.java:254)
      at org.springframework.boot.maven.RepackageMojo.repackage (RepackageMojo.java:232)
```

## 5. ASM Analysis
- **ASM used by spring-boot-maven-plugin**: Handled internally via `spring-core` repackaged ASM (derived from ASM 9.7/older).
- **ASM used by plugins explicitly**: `maven-shade-plugin` and others fetch ASM 9.5, 9.6, and 9.7.
- **ASM required for Java 25**: Java 25 (version 69) requires ASM 9.8 logic.

## 6. Spring Boot Java Compatibility Analysis
- Spring Boot 3.4.x relies on Spring Framework 6.2.x, which officially supports up to Java 23/24. 
- Spring Boot 3.5.x (specifically tested `3.5.4`) includes updated `spring-core` dependencies (e.g. `6.2.9`) and patched loader tools that correctly handle Java 25 class packaging.

## 7. Maven Plugin Analysis
The `spring-boot-maven-plugin` requires no explicit configuration. It inherits versions from `<parent>`. No explicitly pinned plugins are causing dependency convergence issues. 

## 8. Surefire/Failsafe Analysis
`maven-surefire-plugin` (version 3.5.3 inherited) executes successfully on Java 25. The test phase completes before the repackaging failure occurs. Failsafe is not currently executed.

## 9. Candidate Solutions
1. **Downgrade to Java 24/21**: Violates user requirements.
2. **Override `spring-core` inside `spring-boot-maven-plugin`**: Risky, can break plugin internals if APIs changed.
3. **Upgrade Spring Boot to 3.5.x**: Adopts the official maintenance path for Java 25 support.

## 10. Recommended Solution
Upgrade the Spring Boot Parent version from `3.4.3` to the stable `3.5.4` release. This provides out-of-the-box Java 25 packaging compatibility without requiring any architectural or Phase 2 domain changes.

## 11. Risks
Minor transitive dependency bumps across the Spring ecosystem (e.g., Spring Security, Spring Data). Given the lack of domain code (Phase 1), regression risk is negligible.

## 12. Files that would need modification
- `backend/pom.xml` (Update `<parent><version>` to `3.5.4`)

## 13. Confirmation that Phase 2 is untouched
I have audited the repository. The domain packages remain completely empty, preserving the Phase 1 foundation state.
