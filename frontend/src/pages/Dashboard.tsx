import { Shield, Activity, FileWarning, Search, Radio } from 'lucide-react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { EmptyState } from '../components/ui/empty-state';
import { useEffect, useState } from 'react';
import { useLiveSecurityEvents } from '@/api/liveEvents';
import { motion, AnimatePresence } from 'framer-motion';

interface DashboardMetrics {
  totalAnalyses: number;
  highRiskAnalyses: number;
  fraudAnalyses: number;
  eventLogAnalyses: number;
  cveAnalyses: number;
}

const Dashboard = () => {
  const [metrics, setMetrics] = useState<DashboardMetrics | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  
  const { events, status } = useLiveSecurityEvents();

  useEffect(() => {
    const fetchMetrics = async () => {
      try {
        const res = await fetch('/api/v1/dashboard/metrics');
        if (!res.ok) throw new Error('Failed to fetch dashboard metrics');
        const json = await res.json();
        if (json.success) {
          setMetrics(json.data);
        } else {
          throw new Error(json.message || 'Error loading metrics');
        }
      } catch (err: any) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchMetrics();
  }, []);

  const getSeverityColor = (sev: string | null) => {
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
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
        <div>
          <h1 className="text-3xl font-bold tracking-tight text-foreground flex items-center gap-3">
            Overview
            {status === 'LIVE' && (
              <span className="flex items-center text-xs font-semibold px-2 py-1 bg-green-500/10 text-green-600 rounded-full border border-green-500/20">
                <Radio className="h-3 w-3 mr-1 animate-pulse" /> LIVE
              </span>
            )}
            {status === 'CONNECTING' && (
              <span className="flex items-center text-xs font-semibold px-2 py-1 bg-yellow-500/10 text-yellow-600 rounded-full border border-yellow-500/20">
                <Radio className="h-3 w-3 mr-1" /> CONNECTING
              </span>
            )}
            {status === 'OFFLINE' && (
              <span className="flex items-center text-xs font-semibold px-2 py-1 bg-red-500/10 text-red-600 rounded-full border border-red-500/20">
                <Radio className="h-3 w-3 mr-1" /> OFFLINE
              </span>
            )}
          </h1>
          <p className="text-muted-foreground mt-1">Platform intelligence and system status.</p>
        </div>
      </div>

      {/* Metric Cards Shell */}
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total Analyses</CardTitle>
            <Activity className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{loading ? '--' : metrics?.totalAnalyses || 0}</div>
            <p className="text-xs text-muted-foreground">Across all modules</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">High Risk Findings</CardTitle>
            <Shield className="h-4 w-4 text-red-500" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold text-red-500">{loading ? '--' : metrics?.highRiskAnalyses || 0}</div>
            <p className="text-xs text-muted-foreground">Requires attention</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Fraud Analyses</CardTitle>
            <FileWarning className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{loading ? '--' : metrics?.fraudAnalyses || 0}</div>
            <p className="text-xs text-muted-foreground">Transactions checked</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">CVEs Queried</CardTitle>
            <Search className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{loading ? '--' : metrics?.cveAnalyses || 0}</div>
            <p className="text-xs text-muted-foreground">Vulnerabilities tracked</p>
          </CardContent>
        </Card>
      </div>

      {/* Main Charts/Activity Shell */}
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-7">
        <Card className="lg:col-span-4">
          <CardHeader>
            <CardTitle>Security Activity</CardTitle>
            <CardDescription>Event log activity and threat detection over time.</CardDescription>
          </CardHeader>
          <CardContent className="pl-2">
            <EmptyState 
              icon={Activity} 
              title={error ? "Error Loading Data" : "No activity data available"} 
              description={error ? error : "Run an analysis to generate insights and populate activity charts."} 
            />
          </CardContent>
        </Card>
        <Card className="lg:col-span-3">
          <CardHeader>
            <CardTitle className="flex items-center justify-between">
              Live Security Stream
            </CardTitle>
            <CardDescription>Real-time events processed by the Incident Engine.</CardDescription>
          </CardHeader>
          <CardContent>
            {events.length === 0 ? (
              <EmptyState 
                icon={Radio} 
                title="No recent security activity"
                description="Waiting for new events from RabbitMQ stream..." 
              />
            ) : (
              <div className="space-y-3 h-[400px] overflow-y-auto pr-2">
                <AnimatePresence initial={false}>
                  {events.map((ev, i) => (
                    <motion.div 
                      key={`${ev.eventId}-${i}`}
                      initial={{ opacity: 0, y: -20, scale: 0.95 }}
                      animate={{ opacity: 1, y: 0, scale: 1 }}
                      transition={{ duration: 0.3 }}
                      className="p-3 rounded-lg border border-border bg-card/50 flex flex-col gap-2"
                    >
                      <div className="flex justify-between items-start">
                        <span className="font-semibold text-sm">{ev.eventType}</span>
                        {ev.severity && (
                          <span className={`text-xs px-2 py-0.5 rounded-full border ${getSeverityColor(ev.severity)}`}>
                            {ev.severity}
                          </span>
                        )}
                      </div>
                      <div className="flex justify-between items-end">
                        <span className="text-xs text-muted-foreground truncate max-w-[200px]" title={ev.entityReferenceId}>
                          {ev.entityReferenceId}
                        </span>
                        <span className="text-xs text-muted-foreground">
                          {new Date(ev.timestamp).toLocaleTimeString()}
                        </span>
                      </div>
                    </motion.div>
                  ))}
                </AnimatePresence>
              </div>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default Dashboard;
