# Phase 7: Responsive & Accessibility

## Responsive Design
- The layout uses Tailwind's mobile-first breakpoints (`sm:`, `md:`, `lg:`).
- Header elements stack vertically on mobile and align horizontally on desktop.
- The 4-column metric cards gracefully collapse into a single column on mobile viewports.

## Accessibility
- Severity indicators do not rely on color alone; they explicitly spell out the severity string (`CRITICAL`, `HIGH`, etc.).
- Contrast ratios respect the Sentrix light-theme design system.
- Standard button elements and focus rings are maintained via `shadcn/ui` foundation.
