# UI/UX Contract

## Visual Identity
* **Theme**: Futuristic but professional, representing a premium cybersecurity SaaS console.
* **Mode**: Dark Mode default (dark gray/navy backgrounds, high contrast text).
* **Primary Accents**: Electric Blue, Neon Cyan.
* **Risk Colors**: 
  * CRITICAL: Crimson/Red
  * HIGH: Orange
  * MEDIUM: Yellow/Amber
  * LOW: Emerald/Green

## Technologies
* **CSS Framework**: Tailwind CSS.
* **Component Library**: shadcn/ui.
* **Animations**: Framer Motion (subtle micro-animations for interactions, page transitions).
* **Charting**: Recharts (responsive, data-dense visuals).

## Layout Structure
* **Sidebar**: Global navigation (Dashboard, Fraud, Event Logs, CVE, Chat, History, Reports).
* **Top Header**: Breadcrumbs, Session Status indicator, global system health ping.
* **Content Area**: Max-width constrained for readability on ultrawide monitors, responsive grid layout for widgets.

## Components
* **Cards**: Used to encapsulate discrete pieces of information (e.g., Risk Score widget, single Threat Indicator).
* **Data Tables**: Paginated, sortable tables for Event Logs and Analysis History.
* **Forms**: Validation via React Hook Form/Zod. Clear error messages inline.
* **Dialogs**: Used for confirmation (e.g., "Are you sure you want to delete this report?").
* **Badges**: pill-shaped indicators for Risk Levels and Statuses (PENDING, COMPLETED).

## Data Integrity Rule
**IMPORTANT**: The frontend must NOT hardcode fake dashboard statistics, mock vulnerabilities, or simulated AI responses in the final build. All data rendered must originate from the backend API.
