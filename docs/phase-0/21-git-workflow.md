# Phase 0: Git Workflow

This document defines the Git branching and commit strategies for Sentrix AI development.

## 1. Branching Strategy

A simplified, practical feature-branch workflow will be used.

### Main Branches
*   **`main`**: The stable, production-ready branch. Code here must always be deployable. Direct commits to `main` are forbidden.
*   **`develop`**: The primary integration branch. All completed features are merged here before release to `main`.

### Supporting Branches
Created off `develop` and merged back into `develop` via Pull Request (PR).
*   **Feature Branches**: `feature/[short-description]`
    *   *Example*: `feature/cve-api-integration`
    *   Used for new capabilities.
*   **Bug Fix Branches**: `fix/[short-description]`
    *   *Example*: `fix/session-timeout-bug`
    *   Used for resolving bugs found in `develop`.
*   **Refactor Branches**: `refactor/[short-description]`
    *   *Example*: `refactor/risk-scoring-engine`
    *   Used for structural changes that do not alter external behavior.

## 2. Commit Message Conventions

Commit messages must be descriptive and follow the Conventional Commits structure to allow for automated changelog generation and clear history.

**Format**:
`<type>: <description>`

**Allowed Types**:
*   `feat`: A new feature.
*   `fix`: A bug fix.
*   `docs`: Documentation only changes.
*   `style`: Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc).
*   `refactor`: A code change that neither fixes a bug nor adds a feature.
*   `perf`: A code change that improves performance.
*   `test`: Adding missing tests or correcting existing tests.
*   `chore`: Changes to the build process or auxiliary tools and libraries.
*   `security`: Specific security hardening or vulnerability patching.

**Examples**:
*   `feat: implement anonymous session initialization`
*   `fix: correct evtx parsing null pointer exception`
*   `docs: update database ER diagram`

## 3. Pull Request (PR) Process

1.  Developer completes work on a `feature/*` branch.
2.  Developer pushes the branch and opens a Pull Request against `develop`.
3.  The PR must include a clear description of the changes.
4.  (Ideal) Another team member reviews the code.
5.  CI/CD pipeline runs automated checks (build, tests).
6.  Upon approval and successful checks, the PR is merged into `develop` using "Squash and Merge" to maintain a clean history.
