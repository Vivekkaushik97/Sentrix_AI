# Database Security Model

## Access Boundaries
* **Application DB User**: The Spring Boot application connects using a role restricted to DML operations (`SELECT`, `INSERT`, `UPDATE`, `DELETE`). It does NOT have schema alteration privileges in production.
* **Secret Management**: Database URL and credentials are provided purely via Environment Variables (`DATABASE_URL`).
* **SQL Injection**: Prevented natively by Spring Data JPA Criteria API and Hibernate PreparedStatement parameter binding. 
  * *Note: JPA does not mathematically make SQL injection impossible (e.g., string concatenation in native queries), but strict adherence to repository parameters eliminates the risk.*
* **Least Privilege**: The AI subsystem has read access only to RAG contexts. It cannot alter database tables.
