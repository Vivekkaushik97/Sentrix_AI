# File Upload Contract

Used primarily in `/api/v1/event-logs/upload`.

## Rules
* **Content-Type**: `multipart/form-data`.
* **Field Name**: `file`.
* **Allowed Types**: `.evtx` (Application/octet-stream, specific magic bytes verification TBD).
* **Size Limits**: Max 50MB (TBD configurable in Spring Boot `spring.servlet.multipart.max-file-size`).
* **Validation**: Backend must reject `.exe`, `.sh`, etc. immediately.
* **Lifecycle**: Files are written to temporary storage. Immediately deleted upon RabbitMQ job completion (`COMPLETED` or `FAILED`).

*Note: Storage mechanism (Local temp dir vs S3) is TBD.*
