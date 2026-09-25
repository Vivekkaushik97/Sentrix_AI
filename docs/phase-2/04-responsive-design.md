# Responsive Design Strategy

The application is built Mobile-First using Tailwind breakpoints:
- `sm`: 640px
- `md`: 768px
- `lg`: 1024px
- `xl`: 1280px

## Key Implementations
- **Sidebar**: Hidden entirely on mobile devices, transitioning to a toggleable drawer/menu (via the TopNav hamburger icon).
- **Dashboard Grid**: Uses 1 column on mobile, transitioning to 2 columns on tablet, and 4 or 7-column custom spans on large desktops (`lg:col-span-4`, etc.).
- **Typography**: Scales fluidly and truncates where horizontal real estate is lacking.
