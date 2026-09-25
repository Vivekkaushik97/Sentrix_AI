# UI/UX Design System

The Sentrix AI platform uses a dark-mode first design system tailored for a premium cybersecurity experience.

## Theme Semantics

- **Backgrounds**: Slate/dark slate colors (`230 25% 10%`).
- **Surfaces/Cards**: Slightly elevated dark slate.
- **Primary Accent**: Vibrant electric blue/cyan (`210 100% 60%`) representing technical sophistication and active intelligence.
- **Typography**: Inter/system fonts with strict hierarchy.

## Semantic Risk Indicators
We established dedicated Tailwind semantic variables rather than hard-coding hex colors:
- `success`: Represents safe, clean, or completed operations.
- `warning`: Represents low/medium severity risks or pending states.
- `destructive`: Represents high/critical threats, active attacks, or failure states.
- `info`: Represents intelligence, neutral alerts, or AI insights.

## Motion & Polish
Animations and transitions are extremely subtle using `tailwindcss-animate` and CSS transitions, targeting reduced motion where required.
