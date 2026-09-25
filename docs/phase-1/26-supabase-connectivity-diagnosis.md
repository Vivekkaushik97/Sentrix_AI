# Supabase Connectivity Diagnosis

## Objective
Verify the runtime connectivity between the local Spring Boot application and the remote Supabase PostgreSQL database using the IPv4-compatible Supabase Session Pooler.

## Provided Configuration
- **Host**: `aws-0-ap-south-1.pooler.supabase.com`
- **Port**: `5432`
- **Database**: `postgres`
- **Project Ref**: `utlgyswtzpgnrzogpyfl`
- **Username**: `postgres.utlgyswtzpgnrzogpyfl`

## 1. Network & DNS Verification
The host was verified using `Resolve-DnsName`.
**Result**: The DNS query successfully returned IPv4 `A` records (e.g., `65.0.195.55`).
**Conclusion**: IPv6 routing issue resolved. DNS resolution is successful.

## 2. Reachability Verification
A TCP connection test was executed via `Test-NetConnection -ComputerName aws-0-ap-south-1.pooler.supabase.com -Port 5432`.
**Result**: `TcpTestSucceeded : True`.
**Conclusion**: Basic TCP connectivity on port 5432 is successfully established.

## 3. Spring Boot Runtime Verification
The `.env` variables were explicitly injected into the process environment and `mvnw spring-boot:run` was executed.
**Result**: Spring Boot failed to start.
**Stacktrace Excerpt**:
```
Caused by: org.postgresql.util.PSQLException: The connection attempt failed.
Caused by: java.net.SocketTimeoutException: Read timed out
	...
	at org.postgresql.core.v3.ConnectionFactoryImpl.enableSSL(ConnectionFactoryImpl.java:627)
```
**Cause**: The JDBC driver establishes a TCP connection but times out during the TLS/SSL handshake (`enableSSL`).

## Conclusion and Resolution

**Diagnosis:**
1. The TLS handshake timed out on port `5432` because Supabase's IPv4 connection pooler natively requires port `6543` (Transaction Pooler) for correctly terminated TLS connections through the shared domain. 
2. Additionally, using the Transaction Pooler causes `PSQLException: ERROR: prepared statement already exists` unless the JDBC driver disables server-side prepared statements.

**Resolution:**
1. Modified the `.env` file to use port `6543`.
2. Added `&prepareThreshold=0` to `SPRING_DATASOURCE_URL` in `.env` to prevent prepared statement caching errors with the Supabase Transaction Pooler.
3. Added the `flyway-database-postgresql` dependency to `pom.xml` to fix Flyway 11 incompatibility with PostgreSQL 17.6.

**Result:**
The backend successfully started on port `8080`, and Flyway completed initialization. The `/api/v1/health` endpoint returned HTTP 200 `{"status":"UP"}`. All validation checks for Phase 1 are now securely passing.
