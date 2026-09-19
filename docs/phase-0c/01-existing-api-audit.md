# Existing API Audit

## Overview
Phase 0 initially drafted high-level API groups in `05-api-contract.md`. Phase 0B extensively detailed the database architecture. This audit identifies gaps and aligns the API with the finalized data layer.

## Existing Endpoint Groups
* `/api/v1/health`
* `/api/v1/session`
* `/api/v1/fraud`
* `/api/v1/event-logs`
* `/api/v1/cves`
* `/api/v1/chat`
* `/api/v1/rag`
* `/api/v1/analyses`
* `/api/v1/reports`
* `/api/v1/dashboard`

## Strengths
* RESTful structure is defined.
* Routes naturally map to the business domains.
* Pure anonymous session approach simplifies identity.

## Missing Details & Ambiguities
* **Response Envelope**: No consistent envelope (e.g., `{ data: ... }`) was finalized.
* **Pagination/Sorting**: Not detailed how it's handled via URL params.
* **Async Operations**: Unclear how RabbitMQ jobs return status to the frontend (polling vs. SSE).
* **Uploads**: Event log EVTX upload chunking or limits are undefined.
* **History**: The UI requires `/history`, but the API only proposes `/analyses`. We must confirm if `/analyses` fulfills history requirements.

## Conflicts with Phase 0B
* RAG: Phase 0B defines `knowledge_documents` and `knowledge_chunks`. Does the frontend need to manage these, or is it an internal admin task? The API shouldn't expose RAG ingestion publicly if it's an internal process.
* `analysis_records`: Phase 0B specifies a 1:1 relationship with specific analyses. The API must clearly define how to fetch the generic record vs. the specific detail.

## Required Refinements
* Establish universal error and response envelopes.
* Finalize the async polling mechanism.
* Define exact HTTP methods and request/response payloads for all endpoints.
