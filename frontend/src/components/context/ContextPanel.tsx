import { useEffect, useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Network, GitPullRequest, Bot } from 'lucide-react';
import { getSecurityContext } from '@/api/context';
import type { SecurityContextDto } from '@/types/context';
import { askAi } from '@/api/ai';

interface ContextPanelProps {
  sourceType: string;
  sourceId: string;
}

export function ContextPanel({ sourceType, sourceId }: ContextPanelProps) {
  const [context, setContext] = useState<SecurityContextDto | null>(null);
  const [loading, setLoading] = useState(true);
  const [aiSummary, setAiSummary] = useState('');
  const [aiLoading, setAiLoading] = useState(false);

  useEffect(() => {
    const fetchContext = async () => {
      try {
        setLoading(true);
        const data = await getSecurityContext(sourceType, sourceId);
        setContext(data);
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    };
    fetchContext();
  }, [sourceType, sourceId]);

  const handleExplain = async () => {
    if (!context) return;
    setAiLoading(true);
    try {
      const payload = JSON.stringify({
        source: context.source,
        id: context.entityId,
        graphNodes: context.graph.nodes,
        graphEdges: context.graph.edges
      });
      const res = await askAi({
        query: "Explain this security context graph. What are the relationships?",
        context: payload
      });
      setAiSummary(res.response);
    } catch (error) {
      console.error(error);
    } finally {
      setAiLoading(false);
    }
  };

  if (loading) return <div className="h-48 animate-pulse bg-muted rounded-xl border border-border" />;
  if (!context) return <div className="p-6 text-center text-muted-foreground border border-border rounded-xl">No security context available.</div>;

  return (
    <div className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <Network className="w-5 h-5" /> Context Graph
            </CardTitle>
            <CardDescription>Deterministic relationships across domains</CardDescription>
          </CardHeader>
          <CardContent>
            {context.graph.nodes.length <= 1 ? (
              <p className="text-muted-foreground text-sm">No related context nodes found.</p>
            ) : (
              <div className="space-y-2">
                {context.graph.edges.map((e, idx) => (
                  <div key={idx} className="flex items-center gap-2 text-sm bg-muted p-2 rounded">
                    <span className="font-mono text-xs">{e.sourceId.substring(0, 8)}</span>
                    <GitPullRequest className="w-3 h-3 text-muted-foreground mx-1" />
                    <span className="font-mono text-xs">{e.targetId.substring(0, 8)}</span>
                    <span className="text-muted-foreground text-xs italic ml-auto">{e.relationshipType}</span>
                  </div>
                ))}
              </div>
            )}
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <Bot className="w-5 h-5" /> Context AI
            </CardTitle>
            <CardDescription>Explain the localized graph</CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <Button className="w-full" variant="outline" onClick={handleExplain} disabled={aiLoading}>
              {aiLoading ? "Analyzing Context..." : "Explain Relationships"}
            </Button>
            {aiSummary && (
              <div className="p-4 bg-primary/10 text-primary-foreground text-sm rounded-md border border-primary/20">
                <p className="whitespace-pre-wrap">{aiSummary}</p>
              </div>
            )}
          </CardContent>
        </Card>
      </div>

      {context.provenance.length > 0 && (
        <p className="text-xs text-muted-foreground text-center">
          Provenance: {context.provenance.join(', ')}
        </p>
      )}
    </div>
  );
}
