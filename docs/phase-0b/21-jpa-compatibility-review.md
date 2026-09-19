# JPA/Hibernate Compatibility Review

## Straightforward Mappings
* Most tables map 1:1 to Java `@Entity` classes using `UUID` types for primary keys.
* `TIMESTAMPTZ` maps to `java.time.OffsetDateTime`.

## Relational Mappings
* The `analysis_records` split is highly compatible with Hibernate's `@Inheritance(strategy = InheritanceType.JOINED)`.
  * Alternatively, simple `@OneToOne` mappings between `AnalysisRecord` and `FraudAnalysis` can be used.

## Complex Mappings (Implementation Spikes Required)
* **JSONB**: Hibernate requires custom types (e.g., Hypersistence Utils) or JPA 2.1 converters to map `JSONB` directly to Java `Map<String, Object>` or Jackson `JsonNode`.
* **pgvector**: Spring AI integrates with pgvector via the `JdbcTemplate` vector store implementation natively. JPA mapping of the `VECTOR` type is generally bypassed in favor of letting Spring AI manage the `knowledge_chunks` table directly.
  * **Status**: REQUIRES IMPLEMENTATION SPIKE to confirm if Spring AI's default pgvector schema conflicts with custom JPA entities, or if they should remain strictly separated.
