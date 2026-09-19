# Git Workflow

Sentrix AI utilizes a streamlined feature-branch workflow suited for Capstone project development.

## Branches
* `main`: Production-ready, stable code.
* `develop`: Integration branch for active development.
* `feature/[name]`: New features (e.g., `feature/fraud-detection-ui`).
* `fix/[name]`: Bug fixes (e.g., `fix/session-timeout-bug`).
* `refactor/[name]`: Code structural changes without feature additions.

## Commit Message Conventions
Based on Conventional Commits:
* `feat:` - A new feature.
* `fix:` - A bug fix.
* `docs:` - Documentation only changes (e.g., Phase 0 updates).
* `refactor:` - Code change that neither fixes a bug nor adds a feature.
* `test:` - Adding missing tests or correcting existing ones.
* `chore:` - Build process or auxiliary tool changes.

**Example**: `feat: implement RabbitMQ producer for event logs`

## Process
1. Branch off `develop`.
2. Commit changes using conventions.
3. Open a Pull Request against `develop`.
4. Review and merge.
5. `develop` is merged into `main` at milestone releases.
