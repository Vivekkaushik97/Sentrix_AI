import { motion, useReducedMotion } from 'framer-motion';
import { useEffect, useState } from 'react';

// Generates a random set of nodes/particles
const generateNodes = (count: number) => {
  return Array.from({ length: count }).map((_, i) => ({
    id: i,
    x: Math.random() * 100,
    y: Math.random() * 100,
    size: Math.random() * 3 + 1,
    duration: Math.random() * 20 + 20,
    delay: Math.random() * 5,
  }));
};

const AnimatedBackground = () => {
  const prefersReducedMotion = useReducedMotion();
  const [nodes, setNodes] = useState<{id: number, x: number, y: number, size: number, duration: number, delay: number}[]>([]);

  useEffect(() => {
    // Fewer nodes on smaller screens could be handled with CSS/media queries, but we'll just spawn a small amount that works everywhere.
    setNodes(generateNodes(15));
  }, []);

  return (
    <div className="absolute inset-0 z-0 overflow-hidden pointer-events-none bg-background">
      {/* Soft Center Radial Light */}
      <div className="absolute inset-0 bg-[radial-gradient(circle_at_50%_50%,rgba(0,100,255,0.04)_0%,transparent_60%)]" />

      {/* Moving Perspective Grid */}
      <div className="absolute inset-0 flex items-center justify-center [perspective:1000px] opacity-30">
        <motion.div
          className="absolute w-[200%] h-[200%] top-0 bg-[linear-gradient(to_right,rgba(150,150,150,0.1)_1px,transparent_1px),linear-gradient(to_bottom,rgba(150,150,150,0.1)_1px,transparent_1px)] bg-[size:4rem_4rem]"
          style={{ transformOrigin: 'center center' }}
          initial={{ rotateX: 60, y: '-20%', backgroundPositionY: '0px' }}
          animate={prefersReducedMotion ? {} : { backgroundPositionY: '64px' }}
          transition={{ repeat: Infinity, duration: 4, ease: 'linear' }}
        />
      </div>

      {/* Abstract Data Lines */}
      {!prefersReducedMotion && (
        <div className="absolute inset-0 opacity-20">
          <motion.div
            className="absolute h-[1px] bg-gradient-to-r from-transparent via-primary/30 to-transparent w-full top-[30%]"
            initial={{ x: '-100%', opacity: 0 }}
            animate={{ x: '100%', opacity: [0, 1, 0] }}
            transition={{ duration: 15, repeat: Infinity, ease: 'linear', delay: 2 }}
          />
          <motion.div
            className="absolute h-[1px] bg-gradient-to-r from-transparent via-primary/30 to-transparent w-full top-[70%]"
            initial={{ x: '100%', opacity: 0 }}
            animate={{ x: '-100%', opacity: [0, 1, 0] }}
            transition={{ duration: 20, repeat: Infinity, ease: 'linear', delay: 5 }}
          />
          <motion.div
            className="absolute w-[1px] bg-gradient-to-b from-transparent via-primary/20 to-transparent h-full left-[40%]"
            initial={{ y: '-100%', opacity: 0 }}
            animate={{ y: '100%', opacity: [0, 1, 0] }}
            transition={{ duration: 25, repeat: Infinity, ease: 'linear', delay: 1 }}
          />
        </div>
      )}

      {/* Connection Nodes / Particles */}
      <div className="absolute inset-0">
        {nodes.map((node) => (
          <motion.div
            key={node.id}
            className="absolute rounded-full bg-primary/20"
            style={{
              left: `${node.x}%`,
              top: `${node.y}%`,
              width: node.size,
              height: node.size,
            }}
            animate={
              prefersReducedMotion
                ? { opacity: [0.2, 0.5, 0.2] }
                : {
                    y: ['-20px', '20px', '-20px'],
                    opacity: [0.1, 0.6, 0.1],
                  }
            }
            transition={{
              duration: node.duration,
              repeat: Infinity,
              delay: node.delay,
              ease: 'easeInOut',
            }}
          >
            {/* Subtle glow on a few nodes */}
            {node.id % 4 === 0 && (
              <div className="absolute inset-0 rounded-full bg-accent/40 blur-[2px] animate-pulse" />
            )}
          </motion.div>
        ))}
      </div>
      
      {/* Vignette/Fade out edges to ensure text readability */}
      <div className="absolute inset-0 bg-[radial-gradient(circle_at_50%_50%,transparent_30%,var(--background)_100%)]" />
    </div>
  );
};

export default AnimatedBackground;
