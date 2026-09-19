# RAG API

## Analysis & Decision
* **Internal Services**: RAG heavily utilizes Spring AI and pgvector (`knowledge_chunks`) internally for the Chat Assistant (`/api/v1/chat`).
* **Public Endpoints**: The frontend does NOT need to manage RAG knowledge bases directly. Ingestion of cybersecurity documents is an administrative/backend task.
* **Conclusion**: There will be **NO PUBLIC `/api/v1/rag` API**.
* The Chat API will internally route queries through the RAG pipeline when appropriate.
