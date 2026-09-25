# Phase 2 Cinematic Landing Background

## Visual Concept
The landing page background was completely redesigned to communicate an initializing intelligence system. Following the reference inspiration, the page now feels like a cinematic, full-screen initialization sequence for a premium cybersecurity platform, moving away from generic SaaS aesthetics while completely avoiding chaotic "hacker/cyberpunk" cliches.

## Animation Layers
1. **Layer 1: Ambient Gradient**: A soft, very low opacity radial gradient that shifts slowly across the background space to provide atmospheric depth.
2. **Layer 2: Cybersecurity Grid**: A light structural grid that uses subtle CSS linear-gradients and a radial mask. It shifts dynamically via mouse parallax.
3. **Layer 3: Intelligence Nodes**: A deterministic set of 12 subtle nodes built with `framer-motion` that slowly orbit their anchor points, simulating abstract data processing.
4. **Layer 4: Scanning Light**: A periodic, slow-moving diagonal scan line (with overlay blend mode) that mimics a systemic intelligence sweep.
5. **Layer 5: Abstract Sentrix Form**: Large, elegant SVG paths (sine curves) representing abstract network topologies that gently rotate and morph over a 25-second cycle.

## Implementation Approach
- **Frontend Architecture**: Two components are primarily responsible: `Landing.tsx` handles the main layout, typography entrance, button interaction, and transition out. `CinematicBackground.tsx` isolates the background layers and accepts `mouseX/Y` props to coordinate parallax.
- **Cinematic Transition**: Upon clicking START, a full-screen masking transition expands over 800ms before triggering the React Router navigation to `/dashboard`.
- **Performance Considerations**: All complex continuous animations use Framer Motion optimized CSS transforms (`x`, `y`, `scale`) and SVG path morphing (`d`). Expensive properties like blur and complex box-shadows were avoided for continuous loops.

## Accessibility / Reduced-Motion Behavior
- Implemented `useReducedMotion` hook.
- When `prefers-reduced-motion: reduce` is active:
  - Mouse parallax is entirely disabled.
  - SVG path morphing stops.
  - The scanning light layer is hidden.
  - Node orbits are stopped (they gently pulse opacity instead).

## Responsive Behavior
- Desktop: Full cinematic layout.
- Mobile: Background layers scale fluidly. Mouse parallax automatically degrades to zero because mobile devices do not trigger `mousemove` coordinates constantly.

## Validation Results
- The landing page transitions beautifully into the `/dashboard`.
- `npm run build` executed successfully.
- `.\mvnw.cmd clean verify` executed successfully.
- Docker containers (RabbitMQ, Redis) remain completely functional.
- Phase 3 was strictly avoided; NO fake data or backend/domain logic was touched.
