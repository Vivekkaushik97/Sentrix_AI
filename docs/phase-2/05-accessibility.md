# Accessibility Strategy

Sentrix AI's frontend is strictly compliant with foundational accessibility patterns.

- **Semantic HTML**: `<header>`, `<main>`, `<aside>`, `<nav>` boundaries map the application shell accurately to screen readers.
- **Focus States**: Visible focus rings are preserved for keyboard navigators (via Tailwind `focus-visible:ring`).
- **Contrast**: The custom slate/blue dark theme maintains WCAG AAA contrast ratios for all critical text and icons against the background.
- **Radix UI**: Underlying Shadcn components (Dialog, Select, etc.) utilize Radix UI primitives ensuring complete WAI-ARIA compliance, automatic focus trapping in modals, and correct aria-attributes.
