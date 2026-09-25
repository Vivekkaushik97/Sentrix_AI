import { Bug } from 'lucide-react';
import { EmptyState } from '../components/ui/empty-state';

const CVEIntelligence = () => {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold tracking-tight text-foreground">CVE Intelligence</h1>
        <p className="text-muted-foreground">Search and analyze Common Vulnerabilities and Exposures.</p>
      </div>
      <EmptyState 
        icon={Bug}
        title="No CVEs searched"
        description="Enter a CVE identifier or search query to retrieve intelligence."
        actionLabel="Search CVE"
        onAction={() => {}}
      />
    </div>
  );
};

export default CVEIntelligence;
