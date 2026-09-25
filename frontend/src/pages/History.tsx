import { History as HistoryIcon } from 'lucide-react';
import { EmptyState } from '../components/ui/empty-state';

const History = () => {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold tracking-tight text-foreground">Analysis History</h1>
        <p className="text-muted-foreground">Review previous analyses and threat hunting queries.</p>
      </div>
      <EmptyState 
        icon={HistoryIcon}
        title="No analysis history"
        description="Your previous analysis results and actions will appear here."
      />
    </div>
  );
};

export default History;
