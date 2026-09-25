import { useEffect, useState } from 'react';
import { Monitor, RefreshCcw, Upload, ShieldAlert, Activity, AlertTriangle, Key } from 'lucide-react';
import { toast } from 'sonner';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from '@/components/ui/card';
import { EmptyState } from '@/components/ui/empty-state';
import { 
  fetchWindowsEvents, 
  fetchWindowsDetections, 
  fetchWindowsCorrelations,
  ingestWindowsEvents 
} from '@/api/windowsEvents';
import type { 
  WindowsEventDto, 
  WindowsEventDetectionDto, 
  WindowsEventCorrelationDto 
} from '@/types/windowsEvents';

const WindowsEvents = () => {
  const [activeTab, setActiveTab] = useState('timeline');
  const [loading, setLoading] = useState(false);
  const [events, setEvents] = useState<WindowsEventDto[]>([]);
  const [detections, setDetections] = useState<WindowsEventDetectionDto[]>([]);
  const [correlations, setCorrelations] = useState<WindowsEventCorrelationDto[]>([]);
  
  // Ingestion State
  const [ingestJson, setIngestJson] = useState('');
  const [ingestLoading, setIngestLoading] = useState(false);
  
  const loadData = async () => {
    setLoading(true);
    try {
      const [eventsRes, detRes, corRes] = await Promise.all([
        fetchWindowsEvents(0, 50),
        fetchWindowsDetections(),
        fetchWindowsCorrelations()
      ]);
      setEvents(eventsRes.data.content || []);
      setDetections(detRes.data || []);
      setCorrelations(corRes.data || []);
    } catch (error: any) {
      toast.error(error.message || "Failed to load Windows Security data");
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
      const parsed = JSON.parse(ingestJson);
      
      if (!parsed.source || !parsed.computerName || !Array.isArray(parsed.events)) {
        throw new Error("Invalid format. Must include source, computerName, and events array.");
      }
      
      await ingestWindowsEvents(parsed);
      toast.success("Windows Events ingested successfully");
      setIngestJson('');
      setActiveTab('timeline');
      loadData();
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
            <Monitor className="h-8 w-8 text-primary" />
            Windows Security Intelligence
          </h1>
          <p className="text-muted-foreground mt-1">
            Investigate Windows security events, detections and correlated behaviors.
          </p>
        </div>
        <div className="flex gap-2">
          <Button variant="outline" onClick={loadData} disabled={loading}>
            <RefreshCcw className={`mr-2 h-4 w-4 ${loading ? 'animate-spin' : ''}`} />
            Refresh
          </Button>
          <Button onClick={() => setActiveTab('ingest')}>
            <Upload className="mr-2 h-4 w-4" />
            Ingest Events
          </Button>
        </div>
      </div>

      <div className="grid gap-4 md:grid-cols-3 lg:grid-cols-4">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Events Analyzed</CardTitle>
            <Activity className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{events.length}</div>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Active Detections</CardTitle>
            <ShieldAlert className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{detections.length}</div>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Correlated Chains</CardTitle>
            <AlertTriangle className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{correlations.length}</div>
          </CardContent>
        </Card>
      </div>

      <div className="space-y-4">
        <div className="flex border-b border-border gap-4">
          <button 
            onClick={() => setActiveTab('timeline')}
            className={`pb-2 text-sm font-medium transition-colors ${activeTab === 'timeline' ? 'border-b-2 border-primary text-primary' : 'text-muted-foreground hover:text-foreground'}`}
          >
            Event Timeline
          </button>
          <button 
            onClick={() => setActiveTab('detections')}
            className={`pb-2 text-sm font-medium transition-colors ${activeTab === 'detections' ? 'border-b-2 border-primary text-primary' : 'text-muted-foreground hover:text-foreground'}`}
          >
            Detections
          </button>
          <button 
            onClick={() => setActiveTab('correlations')}
            className={`pb-2 text-sm font-medium transition-colors ${activeTab === 'correlations' ? 'border-b-2 border-primary text-primary' : 'text-muted-foreground hover:text-foreground'}`}
          >
            Correlations
          </button>
          <button 
            onClick={() => setActiveTab('ingest')}
            className={`pb-2 text-sm font-medium transition-colors ${activeTab === 'ingest' ? 'border-b-2 border-primary text-primary' : 'text-muted-foreground hover:text-foreground'}`}
          >
            Ingest Data
          </button>
        </div>

        {activeTab === 'timeline' && (
          <div className="space-y-4">
            {events.length === 0 && !loading ? (
              <EmptyState 
                icon={Monitor}
                title="No Windows events yet"
                description="Windows security events will appear here after ingestion."
                actionLabel="Ingest Events"
                onAction={() => setActiveTab('ingest')}
              />
            ) : (
              <div className="space-y-4">
                {events.map(event => (
                  <Card key={event.id} className="overflow-hidden hover:shadow-md transition-shadow cursor-pointer">
                    <div className="border-l-4 border-primary p-4">
                      <div className="flex justify-between items-start mb-2">
                        <div className="flex items-center gap-2">
                          <span className="font-bold text-lg">Event ID: {event.eventId}</span>
                          <span className="text-xs px-2 py-1 bg-muted rounded-full">{event.providerName}</span>
                        </div>
                        <span className="text-sm text-muted-foreground">{new Date(event.timestamp).toLocaleString()}</span>
                      </div>
                      <div className="grid grid-cols-2 md:grid-cols-4 gap-4 text-sm mt-4">
                        <div>
                          <span className="text-muted-foreground block text-xs">Host</span>
                          <span className="font-medium">{event.computerName}</span>
                        </div>
                        <div>
                          <span className="text-muted-foreground block text-xs">User</span>
                          <span className="font-medium">{event.user || 'N/A'}</span>
                        </div>
                        <div>
                          <span className="text-muted-foreground block text-xs">Log</span>
                          <span className="font-medium">{event.logName}</span>
                        </div>
                      </div>
                    </div>
                  </Card>
                ))}
              </div>
            )}
          </div>
        )}

        {activeTab === 'detections' && (
          <div className="space-y-4">
            {detections.length === 0 && !loading ? (
              <EmptyState 
                icon={ShieldAlert}
                title="No Security Detections"
                description="The analyzed event set currently contains no matching detection rules."
              />
            ) : (
              <div className="grid gap-4">
                {detections.map(det => (
                  <Card key={det.id}>
                    <CardHeader>
                      <div className="flex justify-between items-start">
                        <div>
                          <CardTitle className="text-lg flex items-center gap-2">
                            <ShieldAlert className="h-5 w-5 text-primary" />
                            Rule: {det.ruleId}
                          </CardTitle>
                          <CardDescription className="mt-1">{new Date(det.createdAt).toLocaleString()}</CardDescription>
                        </div>
                        <span className={`text-xs px-3 py-1 rounded-full border font-medium ${getSeverityColor(det.severity)}`}>
                          {det.severity}
                        </span>
                      </div>
                    </CardHeader>
                    <CardContent>
                      <div className="space-y-4">
                        <div>
                          <h4 className="text-sm font-semibold mb-1">Reason</h4>
                          <p className="text-sm bg-muted p-3 rounded-md">{det.reason}</p>
                        </div>
                        <div>
                          <h4 className="text-sm font-semibold mb-1">Evidence</h4>
                          <p className="text-xs font-mono bg-slate-900 text-slate-300 p-3 rounded-md break-all">{det.evidence}</p>
                        </div>
                      </div>
                    </CardContent>
                  </Card>
                ))}
              </div>
            )}
          </div>
        )}

        {activeTab === 'correlations' && (
          <div className="space-y-4">
            {correlations.length === 0 && !loading ? (
              <EmptyState 
                icon={AlertTriangle}
                title="No Correlated Activity"
                description="No related event sequence has been identified."
              />
            ) : (
              <div className="grid gap-4">
                {correlations.map(corr => (
                  <Card key={corr.id} className="border-t-4 border-t-orange-500">
                    <CardHeader>
                      <div className="flex justify-between items-start">
                        <CardTitle className="flex items-center gap-2">
                          <Key className="h-5 w-5 text-orange-500" />
                          {corr.correlationKey}
                        </CardTitle>
                        <span className={`text-xs px-3 py-1 rounded-full border font-medium ${getSeverityColor(corr.severity)}`}>
                          {corr.severity}
                        </span>
                      </div>
                    </CardHeader>
                    <CardContent>
                      <p className="text-sm mb-4">{corr.explanation}</p>
                      <div className="space-y-2">
                        <h4 className="text-sm font-semibold text-muted-foreground">Correlated Events</h4>
                        {corr.events?.map(ev => (
                          <div key={ev.id} className="bg-muted p-3 rounded-md text-sm flex justify-between items-center border-l-2 border-primary">
                            <span>Event ID: {ev.eventId}</span>
                            <span className="text-muted-foreground">{new Date(ev.timestamp).toLocaleString()}</span>
                          </div>
                        ))}
                      </div>
                    </CardContent>
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
                <CardTitle>Ingest Windows Event JSON</CardTitle>
                <CardDescription>
                  Paste the JSON payload originating from the Windows Event Collector (e.g. PowerShell Get-WinEvent).
                </CardDescription>
              </CardHeader>
              <CardContent>
                <div className="mb-4">
                  <p className="text-xs text-muted-foreground mb-2">Example format:</p>
                  <pre className="text-xs bg-slate-900 text-slate-300 p-2 rounded-md overflow-x-auto">
{`{
  "source": "WIN_COLLECTOR_PS1",
  "computerName": "DESKTOP-HR",
  "events": [
    {
      "timestamp": "2023-10-27T10:00:00Z",
      "logName": "Security",
      "eventId": 4625,
      "user": "Administrator"
    }
  ]
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
                    {ingestLoading ? 'Ingesting...' : 'Submit Events'}
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

export default WindowsEvents;
