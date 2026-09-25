import { motion, AnimatePresence } from 'framer-motion';
import { ArrowRight, ShieldCheck } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import SecurityBootAnimation from '../components/landing/SecurityBootAnimation';

const Landing = () => {
  const navigate = useNavigate();
  const [mousePosition, setMousePosition] = useState({ x: 0, y: 0 });
  const [isTransitioning, setIsTransitioning] = useState(false);

  useEffect(() => {
    const handleMouseMove = (e: MouseEvent) => {
      const x = (e.clientX / window.innerWidth) * 2 - 1;
      const y = (e.clientY / window.innerHeight) * 2 - 1;
      setMousePosition({ x: x * 100, y: y * 100 });
    };

    window.addEventListener('mousemove', handleMouseMove);
    return () => window.removeEventListener('mousemove', handleMouseMove);
  }, []);

  const handleStart = () => {
    setIsTransitioning(true);
    setTimeout(() => {
      navigate('/dashboard');
    }, 1000); // Wait for transition sweep
  };

  return (
    <div className="min-h-screen bg-[#FCFCFA] flex flex-col items-center justify-center p-6 relative overflow-hidden">
      
      {/* Background Water Ripple Animation */}
      <SecurityBootAnimation mouseX={mousePosition.x} mouseY={mousePosition.y} />

      {/* Main Content Area */}
      <AnimatePresence>
        {!isTransitioning && (
          <motion.div 
            className="z-10 flex flex-col items-center text-center max-w-4xl w-full"
            style={{ x: mousePosition.x * 0.01, y: mousePosition.y * 0.01 }}
            exit={{ opacity: 0, scale: 1.1, filter: 'blur(10px)' }}
            transition={{ duration: 0.6, ease: [0.22, 1, 0.36, 1] }}
          >
            {/* 2.5s: Logo/Wordmark Reveal */}
            <motion.div
              initial={{ opacity: 0, filter: 'blur(20px)', y: 20 }}
              animate={{ opacity: 1, filter: 'blur(0px)', y: 0 }}
              transition={{ duration: 1.5, delay: 2.5, ease: "easeOut" }}
              className="mb-4 md:mb-6"
            >
              <h1 className="text-5xl md:text-7xl lg:text-8xl font-bold tracking-tight text-primary">
                SENTRIX AI
              </h1>
            </motion.div>

            {/* 3.0s: Platform Statement */}
            <motion.div
              initial={{ opacity: 0, y: 15 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 1, delay: 3.0, ease: "easeOut" }}
              className="mb-8 md:mb-12 h-8 flex items-center justify-center"
            >
              <p className="text-sm md:text-xl font-light text-muted-foreground tracking-[0.2em] uppercase">
                Cybersecurity Intelligence Platform
              </p>
            </motion.div>

            {/* 3.3s: Boot Status Indicator */}
            <motion.div
              initial={{ opacity: 0, scale: 0.9 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ duration: 0.8, delay: 3.3, ease: "easeOut" }}
              className="mb-12 h-12 flex flex-col items-center justify-center space-y-2"
            >
              <div className="flex items-center space-x-3 text-[10px] md:text-xs font-semibold tracking-widest uppercase bg-white/60 backdrop-blur px-5 py-2.5 rounded-full border border-primary/5 text-muted-foreground">
                <ShieldCheck className="w-4 h-4 text-blue-500" />
                <span>SECURITY SYSTEM READY</span>
              </div>
            </motion.div>

            {/* 3.8s: Start Button */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 1.0, ease: "easeOut", delay: 3.8 }}
              className="h-16 flex items-center justify-center"
            >
              <button
                onClick={handleStart}
                className="group relative inline-flex items-center justify-center gap-4 bg-primary text-primary-foreground px-12 py-4 text-sm md:text-base font-medium rounded-xl overflow-hidden transition-all duration-500 hover:shadow-[0_8px_30px_rgba(0,0,0,0.12)] active:scale-[0.98] focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2"
              >
                <span className="relative z-10 tracking-widest uppercase">Start Sentrix AI</span>
                <ArrowRight className="relative z-10 h-4 w-4 transition-transform duration-500 group-hover:translate-x-1" />
                
                {/* Magnetic Hover Glow */}
                <div className="absolute inset-0 bg-blue-600 opacity-0 group-hover:opacity-10 transition-opacity duration-500" />
              </button>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>

      {/* Cinematic Sweep Transition to Dashboard */}
      <AnimatePresence>
        {isTransitioning && (
          <motion.div
            className="fixed inset-0 z-50 bg-[#FCFCFA] flex items-center justify-center"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ duration: 0.5, ease: "easeOut" }}
          >
             <motion.div
               className="w-full h-full border-[10px] md:border-[20px] border-blue-500/10 rounded-[20px] md:rounded-[30px]"
               initial={{ scale: 0.95, opacity: 0 }}
               animate={{ scale: 1, opacity: 1 }}
               transition={{ duration: 0.6, ease: "easeOut" }}
             />
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
};

export default Landing;
