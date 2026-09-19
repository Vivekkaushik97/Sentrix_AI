# API Error Catalog

Standardized `code` values for the Error Envelope.

## General
* `VALIDATION_ERROR` (400): Bad input.
* `UNAUTHORIZED_SESSION` (401): Missing/Expired session.
* `FORBIDDEN_ACCESS` (403): IDOR attempt blocked.
* `RESOURCE_NOT_FOUND` (404): Entity not found.
* `INTERNAL_SERVER_ERROR` (500): Unhandled exception.

## Domain Specific
* `FILE_TOO_LARGE` (413): Upload size exceeded.
* `INVALID_FILE_TYPE` (400): Not an EVTX file.
* `RATE_LIMIT_EXCEEDED` (429): Quota exhausted.
* `EXTERNAL_API_FAILURE` (502): CVE provider down.
* `AI_PROVIDER_ERROR` (502): LLM provider down.
* `ASYNC_JOB_FAILED` (422): Worker threw an exception parsing logs.
