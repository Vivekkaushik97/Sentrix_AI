import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { ArrowLeft, Briefcase, Clock, Bot } from 'lucide-react';
import { toast } from 'sonner';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from '@/components/ui/card';
import { getInvestigation, getInvestigationTimeline, updateInvestigation } from '@/api/investigations';
import { askAi } from '@/api/ai';
import { ContextPanel } from '@/components/context/ContextPanel';
import type { Investigation, InvestigationTimelineDto } from '@/types/investigations';

export default function InvestigationDetail() {
  const { id } = useParams<{ id: string }>();
  const [investigation, setInvestigation] = useState<Investigation | null>(null);
  const [timeline, setTimeline] = useState<InvestigationTimelineDto[]>([]);
  const [loading, setLoading] = useState(true);
  const [aiSummary, setAiSummary] = useState('');
  const [aiLoading, setAiLoading] = useState(false);

  useEffect(() => {
    const loadData = async () => {
      try {
        if (!id) return;
        const [invData, timelineData] = await Promise.all([
          getInvestigation(id),
          getInvestigationTimeline(id)
        ]);
        setInvestigation(invData);
        setTimeline(timelineData);
      } catch (error) {
        toast.error('Failed to load investigation details');
      } finally {
        setLoading(false);
      }
    };
    loadData();
  }, [id]);

  const handleUpdateStatus = async (status: string) => {
    if (!id) return;
    try {
      const updated = await updateInvestigation(id, { status });
      setInvestigation(updated);
      toast.success(`Investigation marked as ${status}`);
    } catch (error) {
      toast.error('Failed to update status');
    }
  };

  const handleSummarize = async () => {
    if (!investigation) return;
    setAiLoading(true);
    try {
      const context = JSON.stringify({
        title: investigation.title,
        description: investigation.description,
        status: investigation.status,
        timeline: timeline
      });
      const res = await askAi({
        query: "Summarize this security investigation chronologically. Identify key evidence and missing gaps.",
        context
      });
      setAiSummary(res.response);
    } catch (error) {
      toast.error('Failed to generate summary');
    } finally {
      setAiLoading(false);
    }
  };

  if (loading) {
    return <div className="animate-pulse space-y-4">
      <div className="h-32 bg-muted rounded-xl" />
      <div className="h-64 bg-muted rounded-xl" />
    </div>;
  }

  if (!investigation) return <div>Investigation not found.</div>;

  return (
    <div className="space-y-6 animate-in fade-in duration-500 pb-10">
      <div className="flex items-center gap-4">
        <Link to="/investigations">
          <Button variant="ghost" size="icon">
            <ArrowLeft className="w-5 h-5" />
          </Button>
        </Link>
        <div>
          <h1 className="text-3xl font-bold tracking-tight">{investigation.title}</h1>
          <p className="text-muted-foreground flex items-center gap-2 mt-1">
            <Briefcase className="w-4 h-4" /> {investigation.id}
          </p>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card className="md:col-span-2">
          <CardHeader>
            <CardTitle>Investigation Details</CardTitle>
            <CardDescription>{investigation.description}</CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="flex flex-wrap gap-4">
              <div className="p-4 bg-muted rounded-lg flex-1">
                <p className="text-sm text-muted-foreground">Status</p>
                <p className="text-lg font-semibold">{investigation.status}</p>
              </div>
              <div className="p-4 bg-muted rounded-lg flex-1">
                <p className="text-sm text-muted-foreground">Priority</p>
                <p className="text-lg font-semibold">{investigation.priority}</p>
              </div>
              <div className="p-4 bg-muted rounded-lg flex-1">
                <p className="text-sm text-muted-foreground">Evidence Count</p>
                <p className="text-lg font-semibold">{investigation.events?.length || 0}</p>
              </div>
            </div>
            <div className="flex gap-2">
              <Button onClick={() => handleUpdateStatus('IN_PROGRESS')} variant="outline">Mark In Progress</Button>
              <Button onClick={() => handleUpdateStatus('CLOSED')} variant="default">Close Investigation</Button>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <Bot className="w-5 h-5" /> AI Assistant
            </CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <Button className="w-full" onClick={handleSummarize} disabled={aiLoading}>
              {aiLoading ? 'Analyzing...' : 'Summarize Investigation'}
            </Button>
            {aiSummary && (
              <div className="p-4 bg-primary/10 text-primary-foreground text-sm rounded-md border border-primary/20">
                <p className="whitespace-pre-wrap">{aiSummary}</p>
              </div>
            )}
          </CardContent>
        </Card>
      </div>

      <Card>
        <CardHeader>
          <CardTitle className="flex items-center gap-2">
            <Clock className="w-5 h-5" /> Timeline
          </CardTitle>
        </CardHeader>
        <CardContent>
          {timeline.length === 0 ? (
            <p className="text-muted-foreground text-sm">No timeline events recorded.</p>
          ) : (
            <div className="space-y-6">
              {timeline.map((item) => (
                <div key={item.id} className="relative pl-6 pb-4 border-l-2 border-muted last:border-0 last:pb-0">
                  <div className="absolute -left-[5px] top-1 w-2 h-2 rounded-full bg-primary" />
                  <div className="flex justify-between items-start mb-1">
                    <span className="font-medium">{item.eventType}</span>
                    <span className="text-xs text-muted-foreground">
                      {new Date(item.timestamp).toLocaleString()}
                    </span>
                  </div>
                  <p className="text-sm text-muted-foreground">{item.summary}</p>
                  <p className="text-xs font-mono text-muted-foreground mt-1 bg-muted p-1 rounded inline-block">
                    {item.relatedEntityId}
                  </p>
                </div>
              ))}
            </div>
          )}
        </CardContent>
      </Card>
      
      <ContextPanel sourceType="INVESTIGATION" sourceId={id || ''} />
    </div>
  );
}
