# Application Shell Architecture

The frontend uses a foundational `DashboardLayout` implementing a modern App Shell pattern.

## Components

1. **Sidebar** (`src/components/layout/Sidebar.tsx`)
   - Fully collapsible (Icon-only mode vs Full text mode).
   - Contains navigation links mapping to all projected Phase 3+ cybersecurity modules.
   - Preserves branding state and active path intelligence.

2. **Top Navigation** (`src/components/layout/TopNav.tsx`)
   - Contains global search presentation.
   - Contains user profile and notification placeholders.
   - Sticky header for scrollable content areas.

3. **Content Area**
   - Renders React Router's `<Outlet />`.
   - Utilizes `flex-1 overflow-y-auto` allowing the main body to scroll independently of the sidebar.
