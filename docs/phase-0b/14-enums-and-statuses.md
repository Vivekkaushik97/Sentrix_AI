# Enums and Status Values

To enforce data integrity, the following categorical values will be used. (Implementation note: These can be mapped to Java `Enum` types and stored as `VARCHAR` in PostgreSQL to avoid rigid SQL custom types).

### Analysis Status (Technical Lifecycle)
Used in `analysis_records`, `security_reports`.
* `PENDING`: Enqueued in RabbitMQ, waiting for worker.
* `PROCESSING`: Worker has picked up the job.
* `COMPLETED`: Analysis finished successfully.
* `FAILED`: Analysis threw an exception or timed out.

### Risk Classification (Business Output)
Used in `analysis_records`.
* `LOW`: Informational / benign.
* `MEDIUM`: Anomalous.
* `HIGH`: Likely threat.
* `CRITICAL`: Severe, actionable threat.

### Chat Role
Used in `chat_messages`.
* `USER`
* `ASSISTANT`
* `SYSTEM`
