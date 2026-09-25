import { motion, useReducedMotion } from 'framer-motion';
import { useEffect, useState } from 'react';

// Generates pseudo-random deterministic nodes
const generateNodes = (count: number) => {
  return Array.from({ length: count }).map((_, i) => ({
    id: i,
    x: 10 + (Math.sin(i * 4.3) * 40 + 40),
    y: 10 + (Math.cos(i * 3.1) * 40 + 40),
    size: (i % 3) + 2,
    duration: 15 + (i % 10),
    delay: i * 0.2,
  }));
};

interface CinematicBackgroundProps {
  mouseX: number;
  mouseY: number;
}

const CinematicBackground = ({ mouseX, mouseY }: CinematicBackgroundProps) => {
  const prefersReducedMotion = useReducedMotion();
  const [nodes, setNodes] = useState<{id: number, x: number, y: number, size: number, duration: number, delay: number}[]>([]);

  useEffect(() => {
    setNodes(generateNodes(12));
  }, []);

  // Parallax offsets
  const parallaxBg = prefersReducedMotion ? 0 : mouseX * -0.01;
  const parallaxGrid = prefersReducedMotion ? 0 : mouseX * -0.03;
  const parallaxForm = prefersReducedMotion ? 0 : mouseX * -0.05;
  const parallaxNodes = prefersReducedMotion ? 0 : mouseX * 0.04;

  return (
    <div className="absolute inset-0 z-0 overflow-hidden pointer-events-none bg-[#FCFCFA]" aria-hidden="true">
      {/* LAYER 1: Ambient Gradient */}
      <motion.div 
        className="absolute w-[200%] h-[200%] -top-[50%] -left-[50%] opacity-40 mix-blend-multiply"
        style={{ x: parallaxBg, y: prefersReducedMotion ? 0 : mouseY * -0.01 }}
        animate={prefersReducedMotion ? {} : {
          x: ['-5%', '5%', '-5%'],
          y: ['-5%', '5%', '-5%'],
          scale: [1, 1.05, 1],
        }}
        transition={{ duration: 30, repeat: Infinity, ease: 'linear' }}
      >
        <div className="absolute inset-0 bg-[radial-gradient(circle_at_40%_40%,rgba(0,100,255,0.03)_0%,transparent_50%)]" />
        <div className="absolute inset-0 bg-[radial-gradient(circle_at_60%_60%,rgba(150,150,150,0.02)_0%,transparent_60%)]" />
      </motion.div>

      {/* LAYER 5: Abstract Sentrix Form (Placed behind grid for depth) */}
      <motion.div
        className="absolute inset-0 flex items-center justify-center opacity-30"
        style={{ x: parallaxForm, y: prefersReducedMotion ? 0 : mouseY * -0.05 }}
      >
        <motion.svg
          width="120vw"
          height="120vh"
          viewBox="0 0 100 100"
          preserveAspectRatio="none"
          initial={{ opacity: 0, rotate: -5, scale: 0.9 }}
          animate={{ opacity: 1, rotate: 0, scale: 1 }}
          transition={{ duration: 3, ease: 'easeOut', delay: 0.2 }}
        >
          {/* Abstract Data Field */}
          <motion.path
            d="M 10 90 Q 30 10, 60 50 T 90 10"
            fill="none"
            stroke="rgba(0, 100, 255, 0.08)"
            strokeWidth="0.5"
            animate={prefersReducedMotion ? {} : {
              d: [
                "M 10 90 Q 30 10, 60 50 T 90 10",
                "M 10 80 Q 40 20, 50 60 T 90 20",
                "M 10 90 Q 30 10, 60 50 T 90 10"
              ]
            }}
            transition={{ duration: 25, repeat: Infinity, ease: 'easeInOut' }}
          />
          <motion.path
            d="M 20 100 Q 40 20, 70 60 T 100 20"
            fill="none"
            stroke="rgba(0, 0, 0, 0.03)"
            strokeWidth="0.3"
            animate={prefersReducedMotion ? {} : {
              d: [
                "M 20 100 Q 40 20, 70 60 T 100 20",
                "M 10 90 Q 50 10, 60 70 T 90 30",
                "M 20 100 Q 40 20, 70 60 T 100 20"
              ]
            }}
            transition={{ duration: 28, repeat: Infinity, ease: 'easeInOut' }}
          />
        </motion.svg>
      </motion.div>

      {/* LAYER 2: Cybersecurity Grid */}
      <motion.div 
        className="absolute inset-[-50%] opacity-20"
        style={{ x: parallaxGrid, y: prefersReducedMotion ? 0 : mouseY * -0.03 }}
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 2, delay: 0.4 }}
      >
        <div className="absolute inset-0 bg-[linear-gradient(to_right,rgba(0,0,0,0.04)_1px,transparent_1px),linear-gradient(to_bottom,rgba(0,0,0,0.04)_1px,transparent_1px)] bg-[size:5rem_5rem] [mask-image:radial-gradient(ellipse_at_center,black_10%,transparent_70%)]" />
      </motion.div>

      {/* LAYER 4: Scanning Light */}
      {!prefersReducedMotion && (
        <motion.div
          className="absolute inset-0 overflow-hidden mix-blend-overlay opacity-30"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ duration: 2, delay: 0.8 }}
        >
          <motion.div
            className="absolute -top-[100%] -left-[100%] w-[300%] h-[50px] bg-gradient-to-b from-transparent via-primary/5 to-transparent rotate-12"
            animate={{
              y: ['0vh', '250vh'],
            }}
            transition={{
              duration: 8,
              repeat: Infinity,
              ease: 'linear',
              repeatDelay: 5
            }}
          />
        </motion.div>
      )}

      {/* LAYER 3: Intelligence Nodes */}
      <motion.div 
        className="absolute inset-0"
        style={{ x: parallaxNodes, y: prefersReducedMotion ? 0 : mouseY * 0.04 }}
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 2, delay: 1 }}
      >
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
                ? { opacity: [0.1, 0.4, 0.1] }
                : {
                    y: ['-15px', '15px', '-15px'],
                    x: ['-10px', '10px', '-10px'],
                    opacity: [0.1, 0.5, 0.1],
                  }
            }
            transition={{
              duration: node.duration,
              repeat: Infinity,
              delay: node.delay,
              ease: 'easeInOut',
            }}
          >
            {/* Subtle glow for critical nodes */}
            {node.id % 5 === 0 && (
              <div className="absolute inset-[-4px] rounded-full bg-accent/20 blur-[2px] animate-pulse" />
            )}
          </motion.div>
        ))}
      </motion.div>

      {/* Foreground gradient masking to ensure text readability */}
      <div className="absolute inset-0 bg-[radial-gradient(circle_at_50%_50%,transparent_20%,#FCFCFA_100%)] opacity-80" />
    </div>
  );
};

export default CinematicBackground;
