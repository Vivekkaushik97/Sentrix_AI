# Phase 2 Landing Page Animation

## Design Decisions
- **Background**: Implemented a warm off-white background with a soft radial center gradient.
- **Animated Grid**: Implemented a very subtle CSS-driven grid with a 1000px perspective and slow vertical movement to simulate an infinite intelligence plane.
- **Data Paths**: Added thin, low-opacity flowing horizontal/vertical data lines representing abstract cyber intelligence moving through the system.
- **Connection Nodes**: Instantiated 15 slow-moving, low-opacity nodes using Framer Motion that drift gently across the viewport. Subtle pulsing applied selectively.
- **Accessibility/Reduced Motion**: Specifically utilized Framer Motion's `useReducedMotion` hook. When the OS signals reduced motion, the grid stops moving, abstract data lines disappear, and node movement is reduced to simple opacity breathing.

## Validation Results
- The animation sequence starts immediately beneath the `SENTRIX AI` logo and does not block the user from interacting with the `START` button.
- The `START` button retains crisp visibility due to the low-opacity (4-20%) nature of all background technical elements.
- Navigating to `/dashboard` performs exactly as before. No dashboard pages were altered.
- `npm run build` succeeds.
- `mvnw clean verify` succeeds, ensuring backend APIs remain intact.
