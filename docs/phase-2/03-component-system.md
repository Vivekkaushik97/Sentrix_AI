# Reusable Component System

The component architecture heavily relies on Shadcn UI and TailwindCSS for strict consistency.

## Installed Shadcn UI Elements
- `Button`: Multiple variants (default, destructive, outline, secondary, ghost, link).
- `Card`: For dashboard metrics, charts, and information surfaces.
- `Dialog` (Modals): Accessible overlays.
- `Alert`: Semantic page-level messaging.
- `Sonner`: Toast notification system.
- `Skeleton`: For loading states.
- `Table`: For future data grids.
- `Input`, `Label`, `Select`, `Checkbox`, `Switch`, `Radio Group`: Form controls.

## Custom Components
- `EmptyState` (`src/components/ui/empty-state.tsx`): A highly reusable presentation component ensuring "No Fake Data" compliance when modules contain zero records. Accepts an icon, title, description, and optional action.
