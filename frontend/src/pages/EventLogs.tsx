import { FileText } from 'lucide-react';
import { EmptyState } from '../components/ui/empty-state';

const EventLogs = () => {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold tracking-tight text-foreground">Event Logs</h1>
        <p className="text-muted-foreground">Parse and analyze EVTX and system logs.</p>
      </div>
      <EmptyState 
        icon={FileText}
        title="No logs processed"
        description="Upload EVTX or system log files to begin security analysis."
        actionLabel="Upload Logs"
        onAction={() => {}}
      />
    </div>
  );
};

export default EventLogs;
