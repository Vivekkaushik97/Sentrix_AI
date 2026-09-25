# Sentrix AI

Sentrix AI is a comprehensive, Generative-AI-based cybersecurity platform designed to assist human security analysts. It provides real-time security events monitoring, fraud analysis, CVE intelligence, investigation correlation, and controlled security action orchestration, all integrated through an advanced Security Context Graph.

## 🚀 Tech Stack

- **Backend**: Java 21, Spring Boot (3.x), Maven
- **Frontend**: React (Vite), TypeScript, Tailwind CSS, Framer Motion
- **Database**: PostgreSQL (External / Supabase)
- **Messaging/Events**: RabbitMQ (Local Docker)
- **Cache & Idempotency**: Redis (Local Docker)

## 📁 Project Structure

```text
Sentrix_AI/
├── backend/               # Spring Boot Application (api)
│   ├── .mvn/              # Maven Wrapper
│   ├── src/main/java      # Source code (com.sentrix.ai)
│   └── src/main/resources # Flyway migrations, configuration
├── frontend/              # React/Vite Application
│   ├── src/               # React components, pages, context, and layout
│   ├── package.json       # npm dependencies
│   └── tailwind.config.js # Tailwind CSS configuration
├── docs/                  # Architecture, ADRs, Phase specs
├── docker-compose.yml     # Local services (Redis, RabbitMQ)
├── .env.example           # Environment variables template
└── README.md              # This file
```

## 🛠️ Implemented vs Planned Features

### ✅ IMPLEMENTED (Validated in Repository)

- **Infrastructure & Foundation** (Phases 1-3)
  - Full Spring Boot + React + PostgreSQL architectural setup.
  - RabbitMQ event bus and Redis caching integration.
  - Flyway migrations for schema management.
- **Reporting & History** (Phase 4)
  - Security incident and analysis reporting.
- **AI / RAG Assistant** (Phase 5)
  - Advisory LLM assistant for investigations.
- **Windows Security Intelligence** (Phase 6 & 7)
  - Analysis of Windows Event logs and security anomalies.
- **UPI Security & Real-time Incidents** (Phase 8)
  - Real-time event consumption via Server-Sent Events (SSE).
- **Investigations & Security Context Graph** (Phase 9 & 10)
  - Correlation of disparate alerts into unified investigation graphs.
- **Controlled Security Actions** (Phase 11)
  - Strict human-in-the-loop action orchestration with dry-run capabilities.
  - Immutable audit trails for all operations (`ActionAuditEntry`).

### 🚧 PLANNED (Future Work)

- **Autonomous Security Agents** (Phase 12+)
  - Restricted capabilities for more automated, non-destructive response.
- **Advanced Malware Analysis**
- **Production CI/CD & Cloud Deployment**

## ⚙️ Environment Variables

Copy the `.env.example` file to `.env` in the project root:

```ini
# Server
SERVER_PORT=8080

# Database (Supabase PostgreSQL - NOT local docker)
SPRING_DATASOURCE_URL=jdbc:postgresql://[YOUR_SUPABASE_DB_URL]:5432/postgres
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=your_password

# Redis
SPRING_REDIS_HOST=localhost
SPRING_REDIS_PORT=6379
SPRING_REDIS_PASSWORD=

# RabbitMQ
SPRING_RABBITMQ_HOST=localhost
SPRING_RABBITMQ_PORT=5672
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest
```

## 🚀 Running the Application

### 1. Start Local Services

Start RabbitMQ and Redis locally using Docker Compose:

```bash
docker-compose up -d
```

*(Note: PostgreSQL is hosted externally on Supabase and must be provided via `.env`)*

### 2. Start the Backend (API)

```bash
cd backend
# Windows
.\mvnw.cmd clean spring-boot:run

# Linux/Mac
./mvnw clean spring-boot:run
```

The backend runs on `http://localhost:8080`. Flyway migrations will run automatically on startup.

### 3. Start the Frontend (UI)

```bash
cd frontend
npm install
npm run dev
```

The frontend typically runs on `http://localhost:5173`.

## 📚 API Endpoints Overview

The backend uses a standard `/api/v1` prefix.

- **`/api/v1/dashboard`**: Core metrics and statistics.
- **`/api/v1/actions`**: Controlled orchestration logic, approval gates, and executions.
- **`/api/v1/investigations`**: Create and track security investigations.
- **`/api/v1/incidents`**: Security incidents and real-time alerts.
- **`/api/v1/fraud`**: Fraud detection APIs.
- **`/api/v1/assistant`**: AI Chat interface.

For full API specifications, reference the code controllers or any included OpenAPI documentation.

## 🔒 Security Posture

Sentrix AI is built on a **Human-In-The-Loop** orchestration architecture. 
- AI components are strictly **advisory**.
- Action executions require explicit human approval via the **ApprovalGate**.
- Arbitrary command and remote execution are disabled, prioritizing deterministic `DryRunExecutors` and typed bounded handlers.
