import { useState, useEffect } from 'react';
import { CreditCard, Upload, RefreshCcw } from 'lucide-react';
import { toast } from 'sonner';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from '@/components/ui/card';
import { EmptyState } from '@/components/ui/empty-state';
import { ingestUpiTransaction, fetchUpiTransactions } from '@/api/upiSecurity';
import type { UpiTransaction, UpiTransactionDto } from '@/types/upiSecurity';

const UpiSecurity = () => {
  const [activeTab, setActiveTab] = useState('history');
  const [loading, setLoading] = useState(false);
  const [transactions, setTransactions] = useState<UpiTransaction[]>([]);
  
  // Ingestion State
  const [ingestJson, setIngestJson] = useState('');
  const [ingestLoading, setIngestLoading] = useState(false);

  const loadData = async () => {
    setLoading(true);
    try {
      const res = await fetchUpiTransactions(0, 50);
      setTransactions(res.content || []);
    } catch (error: any) {
      toast.error(error.message || "Failed to load UPI transactions");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleIngest = async () => {
    try {
      setIngestLoading(true);
      const parsed: UpiTransactionDto = JSON.parse(ingestJson);
      
      if (!parsed.transactionId || !parsed.amount || !parsed.payerVpa || !parsed.payeeVpa) {
        throw new Error("Invalid format. Must include transactionId, amount, payerVpa, and payeeVpa.");
      }
      
      await ingestUpiTransaction(parsed);
      toast.success("UPI transaction ingested successfully. Processing in background.");
      setIngestJson('');
      setActiveTab('history');
      setTimeout(loadData, 1000); // Wait a bit for backend async processing
    } catch (error: any) {
      toast.error(error.message || "Invalid JSON or ingestion failed");
    } finally {
      setIngestLoading(false);
    }
  };

  const getSeverityColor = (sev: string) => {
    switch(sev?.toUpperCase()) {
      case 'CRITICAL': return 'bg-red-500/10 text-red-600 border-red-500/20';
      case 'HIGH': return 'bg-orange-500/10 text-orange-600 border-orange-500/20';
      case 'MEDIUM': return 'bg-yellow-500/10 text-yellow-600 border-yellow-500/20';
      case 'LOW': return 'bg-blue-500/10 text-blue-600 border-blue-500/20';
      default: return 'bg-slate-500/10 text-slate-600 border-slate-500/20';
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
        <div>
          <h1 className="text-3xl font-bold tracking-tight text-foreground flex items-center gap-3">
            <CreditCard className="h-8 w-8 text-primary" />
            UPI Security Intelligence
          </h1>
          <p className="text-muted-foreground mt-1">
            Analyze UPI transactions for deterministic fraud patterns and anomalies.
          </p>
        </div>
        <div className="flex gap-2">
          <Button variant="outline" onClick={loadData} disabled={loading}>
            <RefreshCcw className={`mr-2 h-4 w-4 ${loading ? 'animate-spin' : ''}`} />
            Refresh
          </Button>
          <Button onClick={() => setActiveTab('ingest')}>
            <Upload className="mr-2 h-4 w-4" />
            Analyze Transaction
          </Button>
        </div>
      </div>

      <div className="space-y-4">
        <div className="flex border-b border-border gap-4">
          <button 
            onClick={() => setActiveTab('history')}
            className={`pb-2 text-sm font-medium transition-colors ${activeTab === 'history' ? 'border-b-2 border-primary text-primary' : 'text-muted-foreground hover:text-foreground'}`}
          >
            Transaction History
          </button>
          <button 
            onClick={() => setActiveTab('ingest')}
            className={`pb-2 text-sm font-medium transition-colors ${activeTab === 'ingest' ? 'border-b-2 border-primary text-primary' : 'text-muted-foreground hover:text-foreground'}`}
          >
            Submit Transaction
          </button>
        </div>

        {activeTab === 'history' && (
          <div className="space-y-4">
            {transactions.length === 0 && !loading ? (
              <EmptyState 
                icon={CreditCard}
                title="No UPI transactions analyzed yet"
                description="Submit a transaction for analysis to see the deterministic fraud engine results here."
                actionLabel="Analyze Transaction"
                onAction={() => setActiveTab('ingest')}
              />
            ) : (
              <div className="space-y-4">
                {transactions.map(tx => (
                  <Card key={tx.id} className="overflow-hidden hover:shadow-md transition-shadow cursor-pointer">
                    <div className="border-l-4 border-primary p-4">
                      <div className="flex justify-between items-start mb-2">
                        <div className="flex items-center gap-2">
                          <span className="font-bold text-lg">Tx ID: {tx.transactionId}</span>
                          <span className={`text-xs px-2 py-1 rounded-full border font-medium ${getSeverityColor(tx.severity)}`}>
                            {tx.severity} (Score: {tx.riskScore}/100)
                          </span>
                        </div>
                        <span className="text-sm text-muted-foreground">{new Date(tx.timestamp).toLocaleString()}</span>
                      </div>
                      <div className="grid grid-cols-2 md:grid-cols-4 gap-4 text-sm mt-4">
                        <div>
                          <span className="text-muted-foreground block text-xs">Amount</span>
                          <span className="font-medium font-mono">{tx.currency} {tx.amount}</span>
                        </div>
                        <div>
                          <span className="text-muted-foreground block text-xs">Payer VPA</span>
                          <span className="font-medium">{tx.payerVpa}</span>
                        </div>
                        <div>
                          <span className="text-muted-foreground block text-xs">Payee VPA</span>
                          <span className="font-medium">{tx.payeeVpa}</span>
                        </div>
                        <div>
                          <span className="text-muted-foreground block text-xs">Status</span>
                          <span className="font-medium">{tx.status}</span>
                        </div>
                      </div>
                    </div>
                  </Card>
                ))}
              </div>
            )}
          </div>
        )}

        {activeTab === 'ingest' && (
          <div className="space-y-4">
            <Card>
              <CardHeader>
                <CardTitle>Submit UPI Transaction</CardTitle>
                <CardDescription>
                  Paste the JSON payload representing the UPI transaction for real-time fraud analysis.
                </CardDescription>
              </CardHeader>
              <CardContent>
                <div className="mb-4">
                  <p className="text-xs text-muted-foreground mb-2">Example format:</p>
                  <pre className="text-xs bg-slate-900 text-slate-300 p-2 rounded-md overflow-x-auto">
{`{
  "transactionId": "TXN987654321",
  "amount": 55000.00,
  "currency": "INR",
  "payerVpa": "user@bank",
  "payeeVpa": "merchant@bank",
  "deviceId": "DEV12345",
  "ipAddress": "192.168.1.100",
  "status": "SUCCESS"
}`}
                  </pre>
                </div>
                <textarea 
                  className="w-full h-64 p-4 font-mono text-sm bg-background border border-input rounded-md focus:ring-2 focus:ring-primary focus:outline-none"
                  placeholder="Paste JSON payload here..."
                  value={ingestJson}
                  onChange={e => setIngestJson(e.target.value)}
                  disabled={ingestLoading}
                />
                <div className="mt-4 flex justify-end">
                  <Button onClick={handleIngest} disabled={!ingestJson.trim() || ingestLoading}>
                    {ingestLoading ? 'Submitting...' : 'Analyze Transaction'}
                  </Button>
                </div>
              </CardContent>
            </Card>
          </div>
        )}
      </div>
    </div>
  );
};

export default UpiSecurity;
