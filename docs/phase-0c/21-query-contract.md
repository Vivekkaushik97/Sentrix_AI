# Pagination & Filtering Contract

Standard query string parameters across all list endpoints.

## Parameters
* `page`: 0-indexed page number (default 0).
* `size`: Records per page (default 20, max 100).
* `sort`: Format `field,direction` (e.g., `createdAt,desc`). Allowed fields are strictly whitelisted to prevent SQL injection.
* `status`: Filter by enum (e.g., `COMPLETED`).
* `type`: Filter by analysis type (e.g., `FRAUD`).

## Response
List endpoints always return the `meta` envelope with total elements and total pages to facilitate UI pagination controls.
