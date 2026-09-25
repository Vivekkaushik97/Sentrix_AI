import { useEffect, useState } from 'react';
import { Briefcase, RefreshCcw } from 'lucide-react';
import { toast } from 'sonner';
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Card } from '@/components/ui/card';
import { EmptyState } from '@/components/ui/empty-state';
import { fetchInvestigations } from '@/api/investigations';
import type { Investigation } from '@/types/investigations';

export default function Investigations() {
  const [investigations, setInvestigations] = useState<Investigation[]>([]);
  const [loading, setLoading] = useState(true);

  const loadData = async () => {
    try {
      setLoading(true);
      const data = await fetchInvestigations();
      setInvestigations(data.content);
    } catch (error) {
      toast.error('Failed to load investigations');
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  return (
    <div className="space-y-6 animate-in fade-in duration-500">
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <div>
          <h1 className="text-3xl font-bold tracking-tight">Investigations</h1>
          <p className="text-muted-foreground mt-1">
            Manage and track active security investigations.
          </p>
        </div>
        <div className="flex gap-2">
          <Button variant="outline" size="sm" onClick={loadData} disabled={loading}>
            <RefreshCcw className={`w-4 h-4 mr-2 ${loading ? 'animate-spin' : ''}`} />
            Refresh
          </Button>
          <Button size="sm">New Investigation</Button>
        </div>
      </div>

      {loading ? (
        <div className="space-y-4">
          {[1, 2, 3].map(i => (
            <Card key={i} className="p-6 h-24 animate-pulse bg-muted" />
          ))}
        </div>
      ) : investigations.length === 0 ? (
        <EmptyState 
          icon={Briefcase} 
          title="No investigations yet" 
          description="There are currently no active investigations in the system." 
        />
      ) : (
        <div className="space-y-4">
          {investigations.map(inv => (
            <Link key={inv.id} to={`/investigations/${inv.id}`} className="block">
              <Card className="p-6 hover:border-primary/50 transition-colors flex justify-between items-center">
                <div>
                  <h3 className="font-semibold text-lg">{inv.title}</h3>
                  <div className="flex gap-4 mt-2 text-sm text-muted-foreground">
                    <span className="flex items-center gap-1">Status: <span className="text-foreground">{inv.status}</span></span>
                    <span className="flex items-center gap-1">Priority: <span className="text-foreground">{inv.priority}</span></span>
                    <span>Created: {new Date(inv.createdAt).toLocaleString()}</span>
                  </div>
                </div>
                <div className="text-right text-sm text-muted-foreground">
                  <div>{inv.events?.length || 0} events</div>
                  <div>{inv.correlations?.length || 0} correlations</div>
                </div>
              </Card>
            </Link>
          ))}
        </div>
      )}
    </div>
  );
}
