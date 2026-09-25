# 02 - Project Structure

The foundation establishes a strict physical separation of concerns:

```
/
├── backend/                  # Java 25 LTS Spring Boot Backend
│   ├── src/main/java/com/sentrix/ai/
│   │   ├── config/           # Foundation configuration
│   │   ├── common/           # Envelopes & global exceptions
│   │   ├── security/         # Foundation security config
│   │   ├── session/          # Session boundaries
│   │   ├── dashboard/        # Dashboard APIs (incl. Health)
│   │   └── (other domains)   # Empty domain boundaries
│   └── pom.xml               # Maven configuration
│
├── frontend/                 # React 18 + Vite Frontend
│   ├── src/                  
│   │   ├── components/       # shadcn/ui components
│   │   ├── lib/              # utilities (cn)
│   │   └── ...               # React foundation
│   ├── vite.config.ts        # Vite config with alias
│   └── tailwind.config.js    # Tailwind v3 config
│
├── docs/                     # Project documentation
│   ├── phase-0/              
│   └── phase-1/              
│
├── docker-compose.yml        # Redis & RabbitMQ
├── .github/workflows/        # CI Pipeline
└── .env.example              # Secret templates
```
