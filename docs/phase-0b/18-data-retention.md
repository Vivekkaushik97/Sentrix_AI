# Data Retention & Deletion

Sentrix AI handles anonymous data. Retention strategies define the system's cleanup mechanisms.

### Temporary / Ephemeral
* **`anonymous_sessions`**: TO BE FINALIZED. Likely a 30-day sliding window. A scheduled job will `DELETE FROM anonymous_sessions WHERE last_active_at < NOW() - INTERVAL '30 days'`.
* **Cascading Effects**: Because all analyses, chats, and reports are FK-linked to `anonymous_sessions` with `ON DELETE CASCADE`, pruning a dead session cleanly removes all associated user data.

### Durable / Global
* **`cve_records`**: Permanent, acting as a global cache.
* **`knowledge_documents` / `knowledge_chunks`**: Permanent, managed manually by administrators.

### Raw Files
* **`uploaded_files` (Storage)**: The physical `.evtx` files should be deleted immediately after `log_analyses` is set to `COMPLETED` to save disk space.
