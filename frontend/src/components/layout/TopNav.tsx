import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Menu as MenuIcon, Bell as BellIcon, Search as SearchIcon, User as UserIcon } from 'lucide-react';

interface TopNavProps {
  toggleSidebar: () => void;
}

const TopNav = ({ toggleSidebar }: TopNavProps) => {
  return (
    <header className="h-16 border-b border-border bg-card flex items-center justify-between px-4 sticky top-0 z-10">
      <div className="flex items-center gap-4">
        <Button variant="ghost" size="icon" onClick={toggleSidebar} className="md:hidden text-muted-foreground">
          <MenuIcon className="h-5 w-5" />
        </Button>
        
        <div className="hidden sm:flex relative w-64 lg:w-96">
          <SearchIcon className="absolute left-2.5 top-2.5 h-4 w-4 text-muted-foreground" />
          <Input 
            type="search" 
            placeholder="Search CVEs, Logs, or IP Addresses..." 
            className="pl-9 bg-secondary/50 border-transparent focus-visible:border-primary"
          />
        </div>
      </div>

      <div className="flex items-center gap-3">
        <Button variant="ghost" size="icon" className="relative text-muted-foreground hover:text-foreground">
          <BellIcon className="h-5 w-5" />
          <span className="absolute top-2 right-2 h-2 w-2 rounded-full bg-primary" />
        </Button>
        <div className="h-8 w-8 rounded-full bg-secondary flex items-center justify-center border border-border">
          <UserIcon className="h-4 w-4 text-muted-foreground" />
        </div>
      </div>
    </header>
  );
};

export default TopNav;
