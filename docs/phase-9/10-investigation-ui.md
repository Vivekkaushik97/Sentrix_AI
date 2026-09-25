# Phase 9: Investigation UI

## Pages Built
1. **List Page** (`/investigations`): Shows all cases sorted by activity, displaying status, priority, and evidence counts. Heavily utilizes the `EmptyState` component.
2. **Detail Page** (`/investigations/:id`):
    - **Header**: Contains action buttons to close cases or update progress.
    - **Timeline**: A visual chronological rendering of `InvestigationTimelineDto`.
    - **AI Assistant**: Embeds the context-aware AI summary block.

## Design Identity
Maintained the established Sentrix aesthetic using `lucide-react` icons (Briefcase, Clock), shadcn Cards, and Tailwind's responsive layouts.
