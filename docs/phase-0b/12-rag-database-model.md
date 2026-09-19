# RAG Database Model

## PostgreSQL + pgvector Structure

### knowledge_documents
Tracks the source files/URLs ingested by the administrator to prevent duplicate ingestion.

### knowledge_chunks
Stores the actual text used for similarity search.
* `content`: The raw text chunk passed to the LLM context window.
* `embedding`: The vector representation of the `content`.
* **Embedding Dimension**: TO BE FINALIZED AFTER EMBEDDING MODEL SELECTION. (e.g., `vector(1536)` for OpenAI, `vector(768)` for others. Must be defined precisely in the SQL migration, thus cannot be implemented yet).

## Vector Distance Strategy
* **Similarity Metric**: Cosine Distance (`<=>`) is typically preferred for text embeddings, but will be mapped appropriately by the pgvector extension once the model is finalized.

## Lifecycle
* Ingested documents are considered static base truth. Versioning is not implemented in this phase; if a document changes, the administrator will delete the `knowledge_document` (cascading to chunks) and re-ingest.
