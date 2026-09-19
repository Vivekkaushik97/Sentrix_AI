# Report Database Model

## Storage Strategy
* **Database Storage**: `security_reports` will hold metadata only (`title`, `status`, `created_at`, `session_id`).
* **Object/File Storage**: The actual PDF binaries will **NOT** be stored in PostgreSQL as `BYTEA` blobs. They will be written to local disk (or S3/Cloud Storage) and referenced via the `storage_path` column.

## Rationale
Storing large PDF binaries in a relational database rapidly increases backup sizes, degrades performance, and wastes expensive DB storage tiers. Object storage referenced by a string column is the industry standard approach for large generated artifacts.
