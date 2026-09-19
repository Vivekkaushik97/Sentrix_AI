# Project Structure - Phase 1

## Final Structure
The project follows a clean repository structure separating frontend, backend, and infrastructure concerns to avoid unnecessary monorepo complexity.

```
/
├── backend/            # Spring Boot Java 21 Application
├── frontend/           # React + Vite TypeScript Application
├── docs/               # Architecture and project documentation
├── infrastructure/     # Shared infrastructure definitions (if any)
├── .github/            # CI/CD workflows
├── .gitignore          # Global gitignore
├── README.md           # Developer experience guide
└── docker-compose.yml  # Local development services (Redis, RabbitMQ)
```

This structure strictly adheres to the Phase 0 architecture boundaries and establishes clear separation of concerns.
