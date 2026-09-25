# Phase 0: UI/UX Contract

This document defines the visual identity and user experience guidelines for Sentrix AI.

## 1. Visual Identity
* **Theme**: Futuristic, professional cybersecurity SaaS. Default Dark Mode.
* **Vibe**: Information-dense, polished, analytical, and fast.
* **Color Palette (Dark Mode)**:
    * *Background*: Very dark blue/slate (e.g., `#0f172a` Tailwind `slate-900`).
    * *Surface/Cards*: Slightly lighter slate (e.g., `#1e293b` Tailwind `slate-800`).
    * *Primary Accent*: Electric Blue or Cyan (`#06b6d4` Tailwind `cyan-500`) for buttons, active links.
    * *Success/Low Risk*: Emerald Green (`#10b981`).
    * *Warning/Medium Risk*: Amber/Orange (`#f59e0b`).
    * *Danger/High Risk*: Crimson Red (`#ef4444`).
* **Typography**:
    * *Primary Font*: Inter (or similar modern sans-serif like Roboto or Outfit).
    * *Monospace*: Fira Code or JetBrains Mono (for logs, CVE IDs, IP addresses).

## 2. Layout Structure
* **Sidebar (Left)**: Persistent navigation menu containing links to Dashboard, Fraud, Event Logs, CVEs, Assistant, History. Collapsible for smaller screens.
* **Header (Top)**: Breadcrumbs, Session ID (truncated), Theme Toggle, Notifications icon.
* **Main Content Area**: Scrollable area for the active route.

## 3. UI Components (shadcn/ui based)
* **Cards**: Used heavily to group related information (e.g., "Risk Summary", "Event Breakdown"). Subtle borders, slight shadow.
* **Data Tables**: Sortable, paginated tables for displaying history, logs, or transactions. Must use monospace for technical identifiers.
* **Badges**: Pill-shaped indicators for Status (Pending/Completed) and Risk Severity (High/Medium/Low).
* **Dialogs/Modals**: Used for confirming actions (e.g., "Are you sure you want to delete this analysis?"). Avoid using modals for complex workflows; use dedicated pages instead.
* **Risk Indicators**: Circular progress rings or gauge charts indicating the 0-100 risk score, color-coded by severity.

## 4. State Management (UX)
* **Loading States**: Use skeleton loaders (`shadcn/ui` Skeleton) that match the shape of the expected content. Avoid full-page blocking spinners unless absolutely necessary.
* **Empty States**: Beautiful illustrations with a clear call-to-action (e.g., "No fraud analyses yet. [Run your first analysis]").
* **Error States**: Non-intrusive toast notifications for minor errors (e.g., "Failed to load latest metrics"). Clear error boundary fallbacks with a "Retry" button for major component failures.

## 5. Animations & Micro-interactions (Framer Motion)
* **Page Transitions**: Subtle fade-in or slide-up when navigating between routes.
* **Hover Effects**: Slight scale-up (1.02) or border color change on interactive elements (cards, table rows, buttons).
* **AI Typing Effect**: The Chat Assistant should simulate typing (streaming effect) when generating responses.

## 6. Data Visualization (Recharts)
* **Risk Trend**: Line chart on the dashboard showing average risk scores over time.
* **Threat Breakdown**: Donut chart showing the distribution of High/Medium/Low threats.
* **Do NOT use fake statistics in the final build.** All charts must handle empty data gracefully.

## 7. Responsiveness
* The UI must be fully functional on standard desktop (1080p) and laptop screens (720p).
* Mobile view should collapse the sidebar into a hamburger menu and stack data table columns where appropriate.
