import { PieChart } from 'lucide-react';
import { EmptyState } from '../components/ui/empty-state';

const Reports = () => {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold tracking-tight text-foreground">Reports</h1>
        <p className="text-muted-foreground">Generate and view executive and technical security reports.</p>
      </div>
      <EmptyState 
        icon={PieChart}
        title="No reports generated"
        description="Run analyses or scheduled scans to generate security reports."
        actionLabel="Generate Report"
        onAction={() => {}}
      />
    </div>
  );
};

export default Reports;
