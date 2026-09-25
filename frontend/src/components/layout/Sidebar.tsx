import { Link, useLocation } from 'react-router-dom';
import { 
  LayoutDashboard, 
  ShieldAlert, 
  FileText, 
  Bug, 
  Bot, 
  PieChart, 
  History,
  Monitor,
  ChevronLeft,
  ChevronRight,
  AlertTriangle,
  Briefcase
} from 'lucide-react';
import { cn } from '@/lib/utils';
import { Button } from '@/components/ui/button';

interface SidebarProps {
  isOpen: boolean;
  setIsOpen: (isOpen: boolean) => void;
}

const navigation = [
  { name: 'Overview', href: '/dashboard', icon: LayoutDashboard },
  { name: 'Investigations', href: '/investigations', icon: Briefcase },
  { name: 'Incidents', href: '/incidents', icon: AlertTriangle },
  { name: 'Windows Security', href: '/windows-events', icon: Monitor },
  { name: 'UPI Security', href: '/upi-security', icon: ShieldAlert },
  { name: 'Fraud Analysis', href: '/fraud', icon: ShieldAlert },
  { name: 'Event Logs', href: '/event-logs', icon: FileText },
  { name: 'CVE Intelligence', href: '/cves', icon: Bug },
  { name: 'AI Assistant', href: '/assistant', icon: Bot },
  { name: 'Reports', href: '/reports', icon: PieChart },
  { name: 'Analysis History', href: '/history', icon: History },
];

const Sidebar = ({ isOpen, setIsOpen }: SidebarProps) => {
  const location = useLocation();

  return (
    <aside 
      className={cn(
        "bg-card border-r border-border transition-all duration-300 flex flex-col h-full",
        isOpen ? "w-64" : "w-20 hidden md:flex"
      )}
    >
      <div className="h-16 flex items-center justify-between px-4 border-b border-border">
        {isOpen && (
          <div className="flex items-center gap-2 font-bold text-xl tracking-tight text-primary">
            <ShieldAlert className="h-6 w-6" />
            <span>SENTRIX AI</span>
          </div>
        )}
        {!isOpen && (
          <div className="w-full flex justify-center text-primary">
            <ShieldAlert className="h-6 w-6" />
          </div>
        )}
      </div>

      <nav className="flex-1 overflow-y-auto py-4 px-3 space-y-1">
        {navigation.map((item) => {
          const isActive = location.pathname.startsWith(item.href);
          return (
            <Link
              key={item.name}
              to={item.href}
              className={cn(
                "flex items-center gap-3 px-3 py-2.5 rounded-md transition-colors group",
                isActive 
                  ? "bg-primary/10 text-primary font-medium" 
                  : "text-muted-foreground hover:bg-secondary hover:text-foreground",
                !isOpen && "justify-center px-0"
              )}
              title={!isOpen ? item.name : undefined}
            >
              <item.icon className={cn("h-5 w-5 shrink-0", isActive ? "text-primary" : "text-muted-foreground group-hover:text-foreground")} />
              {isOpen && <span>{item.name}</span>}
            </Link>
          );
        })}
      </nav>

      <div className="p-4 border-t border-border hidden md:flex">
        <Button 
          variant="ghost" 
          size="icon" 
          onClick={() => setIsOpen(!isOpen)}
          className="w-full flex justify-center text-muted-foreground hover:text-foreground"
        >
          {isOpen ? <ChevronLeft className="h-5 w-5" /> : <ChevronRight className="h-5 w-5" />}
        </Button>
      </div>
    </aside>
  );
};

export default Sidebar;
