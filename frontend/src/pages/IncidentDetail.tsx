import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { ArrowLeft, Activity, List, Bot } from 'lucide-react';
import { toast } from 'sonner';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from '@/components/ui/card';
import { fetchIncidentById } from '@/api/incidents';
import type { SecurityIncident } from '@/types/incidents';
import { ContextPanel } from '@/components/context/ContextPanel';

const IncidentDetail = () => {
  const { id } = useParams<{ id: string }>();
  const [loading, setLoading] = useState(false);
  const [incident, setIncident] = useState<SecurityIncident | null>(null);

  useEffect(() => {
    if (id) {
      loadData(id);
    }
  }, [id]);

  const loadData = async (incidentId: string) => {
    setLoading(true);
    try {
      const res = await fetchIncidentById(incidentId);
      setIncident(res);
    } catch (error: any) {
      toast.error(error.message || "Failed to load incident details");
    } finally {
      setLoading(false);
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

  if (loading || !incident) {
    return (
      <div className="flex justify-center items-center h-64">
        <p className="text-muted-foreground animate-pulse">Loading security intelligence...</p>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center gap-4">
        <Link to="/incidents">
          <Button variant="outline" size="icon">
            <ArrowLeft className="h-4 w-4" />
          </Button>
        </Link>
        <div>
          <h1 className="text-3xl font-bold tracking-tight text-foreground flex items-center gap-3">
            Incident Investigation
          </h1>
          <p className="text-muted-foreground mt-1">
            {incident.id}
          </p>
        </div>
      </div>

      <div className="grid gap-4 md:grid-cols-4">
        <Card className="md:col-span-3">
          <CardHeader>
            <div className="flex justify-between items-start">
              <div>
                <CardTitle className="text-2xl">{incident.title}</CardTitle>
                <CardDescription className="mt-2 text-sm">{incident.description}</CardDescription>
              </div>
              <span className={`px-4 py-1 rounded-full border font-bold text-sm ${getSeverityColor(incident.severity)}`}>
                {incident.severity}
              </span>
            </div>
          </CardHeader>
        </Card>

        <Card>
          <CardHeader className="pb-2">
            <CardTitle className="text-sm font-medium flex items-center gap-2">
              <Activity className="h-4 w-4" />
              Risk Score
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="flex items-end gap-2">
              <span className="text-4xl font-bold">{incident.riskScore}</span>
              <span className="text-muted-foreground mb-1">/ 100</span>
            </div>
            {/* Simple Meter */}
            <div className="w-full bg-slate-200 h-2 mt-4 rounded-full overflow-hidden">
              <div 
                className={`h-full ${incident.riskScore >= 75 ? 'bg-red-500' : incident.riskScore >= 50 ? 'bg-orange-500' : 'bg-yellow-500'}`} 
                style={{ width: `${Math.min(incident.riskScore, 100)}%` }}
              />
            </div>
          </CardContent>
        </Card>
      </div>

      <div className="grid gap-6 md:grid-cols-2">
        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2 text-lg">
              <List className="h-5 w-5 text-primary" />
              Event Timeline
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {incident.events?.length === 0 ? (
                <p className="text-sm text-muted-foreground">No events recorded for this incident.</p>
              ) : (
                incident.events?.map((ev) => (
                  <div key={ev.id} className="relative pl-6 pb-4 border-l-2 border-muted last:border-0 last:pb-0">
                    <div className="absolute -left-[5px] top-1 w-2 h-2 rounded-full bg-primary" />
                    <div className="flex justify-between items-start mb-1">
                      <span className="font-semibold text-sm">{ev.eventType}</span>
                      <span className="text-xs text-muted-foreground">{new Date(ev.timestamp).toLocaleString()}</span>
                    </div>
                    <div className="text-xs font-mono bg-slate-100 p-2 rounded mt-2 break-all text-slate-600">
                      Entity: {ev.entityReferenceId}
                    </div>
                    <p className="text-sm mt-2">{ev.reason}</p>
                  </div>
                ))
              )}
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2 text-lg">
              <Bot className="h-5 w-5 text-primary" />
              AI Explanation
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="bg-slate-900 text-slate-200 p-4 rounded-md text-sm leading-relaxed border border-slate-800">
              <p className="mb-4">
                <span className="font-bold text-primary">System AI:</span> Based on the deterministic findings gathered by the incident engine, this case presents a {incident.severity.toLowerCase()} risk profile.
              </p>
              <p className="mb-4">
                The total risk score of {incident.riskScore}/100 is accumulated from {incident.events?.length || 0} correlated events. Please review the timeline for precise triggers.
              </p>
              <p className="text-xs text-slate-400 italic">
                * Note: AI explanation is strictly grounded in the factual entities linked to this incident. Ask the AI Assistant for deeper contextual exploration.
              </p>
            </div>
          </CardContent>
        </Card>
      </div>

      <div className="mt-6">
        <ContextPanel sourceType="INCIDENT" sourceId={id || ''} />
      </div>

    </div>
  );
}

export default IncidentDetail;
