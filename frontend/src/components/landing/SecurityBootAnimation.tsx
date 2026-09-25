import { motion, useReducedMotion } from 'framer-motion';

interface SecurityBootAnimationProps {
  mouseX: number;
  mouseY: number;
}

const SecurityBootAnimation = ({ mouseX, mouseY }: SecurityBootAnimationProps) => {
  const prefersReducedMotion = useReducedMotion();
  
  // Subtle parallax based on mouse
  const pX = prefersReducedMotion ? 0 : mouseX * 0.02;
  const pY = prefersReducedMotion ? 0 : mouseY * 0.02;

  // Generate deterministic security nodes
  const nodes = [
    { id: 1, x: 20, y: 30, delay: 2.2 },
    { id: 2, x: 75, y: 25, delay: 2.4 },
    { id: 3, x: 50, y: 75, delay: 2.6 },
    { id: 4, x: 30, y: 70, delay: 2.5 },
    { id: 5, x: 80, y: 65, delay: 2.7 },
    { id: 6, x: 15, y: 50, delay: 2.3 },
    { id: 7, x: 85, y: 45, delay: 2.5 },
  ];

  return (
    <div className="absolute inset-0 z-0 overflow-hidden pointer-events-none bg-[#FCFCFA]" aria-hidden="true">
      
      {/* 0.4s: Subtle Grid */}
      <motion.div
        className="absolute inset-0"
        style={{ 
          backgroundImage: 'linear-gradient(to right, rgba(0,0,0,0.03) 1px, transparent 1px), linear-gradient(to bottom, rgba(0,0,0,0.03) 1px, transparent 1px)',
          backgroundSize: '4rem 4rem',
          transformStyle: 'preserve-3d',
          transform: `perspective(1000px) rotateX(45deg) scale(2) translateX(${pX}px) translateY(${pY}px)`,
          transformOrigin: 'top center'
        }}
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 2, delay: 0.4, ease: "easeOut" }}
      >
         {/* Grid highlight sweep */}
         <motion.div 
           className="absolute inset-0 bg-gradient-to-b from-transparent via-blue-500/5 to-transparent h-[20vh] w-full"
           animate={{ top: ['-20%', '120%'] }}
           transition={{ duration: 6, repeat: Infinity, ease: "linear", delay: 2 }}
         />
      </motion.div>

      {/* Center Anchor Point for Parallax */}
      <motion.div 
        className="absolute left-[50%] top-[50%] w-0 h-0"
        style={{ x: pX * 2, y: pY * 2 }}
      >
        
        {/* 1.2s - 2.0s: The Water Drop */}
        {!prefersReducedMotion && (
          <motion.div
            className="absolute left-[-8px] w-4 h-4 rounded-full shadow-[0_4px_10px_rgba(59,130,246,0.3)] backdrop-blur-sm border border-white/50"
            style={{
              background: 'radial-gradient(circle at 30% 30%, rgba(255,255,255,0.9), rgba(59,130,246,0.2) 60%, rgba(0,0,0,0.05))',
            }}
            initial={{ top: '-40vh', scale: 0.5, opacity: 0 }}
            animate={{ 
              top: ['-40vh', '0px', '0px'], 
              scale: [0.5, 1, 0],
              opacity: [0, 1, 0]
            }}
            transition={{ 
              duration: 1.0, 
              delay: 1.0, 
              times: [0, 0.8, 1], // Falls from 1.0s to 1.8s, disappears at impact
              ease: "easeIn" 
            }}
          />
        )}

        {/* 2.0s+: Impact & Ripples */}
        {!prefersReducedMotion && (
          <>
            {/* Core Flash */}
            <motion.div
              className="absolute left-[-50px] top-[-50px] w-[100px] h-[100px] rounded-full bg-blue-500/20 blur-[10px]"
              initial={{ scale: 0, opacity: 0 }}
              animate={{ scale: [0, 1.5, 1], opacity: [0, 1, 0] }}
              transition={{ duration: 1.5, delay: 1.8, ease: "easeOut" }}
            />

            {/* Ripple 1 */}
            <motion.div
              className="absolute left-[-250px] top-[-250px] w-[500px] h-[500px] rounded-full border border-blue-500/20"
              initial={{ scale: 0, opacity: 1 }}
              animate={{ scale: 2.5, opacity: 0 }}
              transition={{ duration: 2.5, delay: 1.8, ease: "easeOut" }}
            />
            {/* Ripple 2 */}
            <motion.div
              className="absolute left-[-200px] top-[-200px] w-[400px] h-[400px] rounded-full border border-primary/10"
              initial={{ scale: 0, opacity: 1 }}
              animate={{ scale: 3, opacity: 0 }}
              transition={{ duration: 3, delay: 1.9, ease: "easeOut" }}
            />
            {/* Ripple 3 */}
            <motion.div
              className="absolute left-[-150px] top-[-150px] w-[300px] h-[300px] rounded-full border border-blue-400/15"
              initial={{ scale: 0, opacity: 1 }}
              animate={{ scale: 4, opacity: 0 }}
              transition={{ duration: 4, delay: 2.0, ease: "easeOut" }}
            />

            {/* Continuous subtle breathing ripples after initial impact */}
            <motion.div
              className="absolute left-[-150px] top-[-150px] w-[300px] h-[300px] rounded-full border border-primary/5"
              initial={{ scale: 1, opacity: 0 }}
              animate={{ scale: [1, 3], opacity: [0, 0.5, 0] }}
              transition={{ duration: 4, delay: 4, repeat: Infinity, ease: "easeOut" }}
            />
          </>
        )}

        {/* Central Sentrix Core (Remains after impact) */}
        <motion.div
          className="absolute left-[-30px] top-[-30px] w-[60px] h-[60px] rounded-full border-2 border-primary/10 flex items-center justify-center bg-[#FCFCFA]/80 backdrop-blur"
          initial={{ scale: 0, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          transition={{ duration: 0.8, delay: prefersReducedMotion ? 0.5 : 1.9, ease: "backOut" }}
        >
           <div className="w-2 h-2 rounded-full bg-blue-500" />
           {/* Rotating security ring */}
           <motion.div 
             className="absolute inset-[-10px] rounded-full border border-dashed border-blue-500/30"
             animate={{ rotate: prefersReducedMotion ? 0 : 360 }}
             transition={{ duration: 10, repeat: Infinity, ease: "linear" }}
           />
        </motion.div>
      </motion.div>

      {/* 0.8s: Technical Nodes Layer */}
      <motion.div 
        className="absolute inset-0"
        style={{ x: pX * 0.5, y: pY * 0.5 }}
      >
        <svg className="absolute inset-0 w-full h-full opacity-30">
          {/* Base static connection lines (appear at 0.8s) */}
          <motion.g
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ duration: 1, delay: 0.8 }}
          >
            {nodes.map(node => (
              <line 
                key={`line-${node.id}`}
                x1="50%" y1="50%" 
                x2={`${node.x}%`} y2={`${node.y}%`} 
                stroke="rgba(0,0,0,0.1)" strokeWidth="1" strokeDasharray="4 4" 
              />
            ))}
          </motion.g>

          {/* Glowing pulse lines that trigger as the ripple hits (2.2s+) */}
          {!prefersReducedMotion && nodes.map(node => (
            <motion.line
              key={`pulse-${node.id}`}
              x1="50%" y1="50%" 
              x2={`${node.x}%`} y2={`${node.y}%`} 
              stroke="rgba(59,130,246,0.3)" strokeWidth="2"
              initial={{ pathLength: 0, opacity: 0 }}
              animate={{ pathLength: [0, 1], opacity: [0, 1, 0] }}
              transition={{ duration: 1.5, delay: node.delay, ease: "easeInOut" }}
            />
          ))}
        </svg>

        {/* The Nodes themselves */}
        {nodes.map(node => (
           <motion.div
             key={`node-${node.id}`}
             className="absolute flex items-center justify-center"
             style={{ left: `${node.x}%`, top: `${node.y}%` }}
             initial={{ scale: 0, opacity: 0 }}
             animate={{ scale: 1, opacity: 1 }}
             transition={{ duration: 0.8, delay: 0.8 }}
           >
             <div className="w-1.5 h-1.5 rounded-full bg-slate-300" />
             {/* Node flash on ripple impact */}
             {!prefersReducedMotion && (
               <motion.div
                 className="absolute w-8 h-8 rounded-full border border-blue-400"
                 initial={{ scale: 0, opacity: 0 }}
                 animate={{ scale: [0, 1.5], opacity: [0, 0.8, 0] }}
                 transition={{ duration: 1, delay: node.delay, ease: "easeOut" }}
               />
             )}
           </motion.div>
        ))}
      </motion.div>

      {/* Abstract Faint Labels */}
      <motion.div
        className="absolute top-[30%] left-[20%] text-[8px] font-mono text-primary/20 tracking-widest"
        initial={{ opacity: 0 }} animate={{ opacity: 1 }} transition={{ delay: 1.5 }}
      >
        SECURITY LAYER
      </motion.div>
      <motion.div
        className="absolute bottom-[30%] right-[20%] text-[8px] font-mono text-primary/20 tracking-widest text-right"
        initial={{ opacity: 0 }} animate={{ opacity: 1 }} transition={{ delay: 1.8 }}
      >
        TRANSACTION CHANNEL<br/>
        INTELLIGENCE SYNC
      </motion.div>

      {/* Vignette Depth Mask */}
      <div className="absolute inset-0 bg-[radial-gradient(circle_at_50%_50%,transparent_10%,#FCFCFA_85%)] opacity-100 pointer-events-none" />
    </div>
  );
};

export default SecurityBootAnimation;
