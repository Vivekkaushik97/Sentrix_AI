import { ShieldAlert } from 'lucide-react';
import { EmptyState } from '../components/ui/empty-state';

const FraudAnalysis = () => {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold tracking-tight text-foreground">Fraud Analysis</h1>
        <p className="text-muted-foreground">Detect and investigate suspicious financial activities.</p>
      </div>
      <EmptyState 
        icon={ShieldAlert}
        title="No transactions analyzed"
        description="Upload transaction data or connect a data source to begin fraud detection."
        actionLabel="New Analysis"
        onAction={() => {}}
      />
    </div>
  );
};

export default FraudAnalysis;
