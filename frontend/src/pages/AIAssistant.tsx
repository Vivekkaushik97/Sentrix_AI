import { Bot, Send } from 'lucide-react';
import { useState } from 'react';

const AIAssistant = () => {
  const [prompt, setPrompt] = useState('');
  const [loading, setLoading] = useState(false);
  const [response, setResponse] = useState<any>(null);
  const [error, setError] = useState<string | null>(null);

  const handleAsk = async () => {
    if (!prompt.trim()) return;
    setLoading(true);
    setError(null);
    setResponse(null);
    
    try {
      const res = await fetch('/api/v1/ai/ask', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ prompt })
      });
      const data = await res.json();
      if (!res.ok) throw new Error(data.message || 'Failed to fetch AI response');
      setResponse(data.data);
    } catch (err: any) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="space-y-6 flex flex-col h-[80vh]">
      <div>
        <h1 className="text-3xl font-bold tracking-tight text-foreground">AI Assistant</h1>
        <p className="text-muted-foreground">Interact with Sentrix AI for deep security insights based on your real data.</p>
      </div>
      
      <div className="flex-1 bg-card border border-border rounded-lg p-6 overflow-y-auto flex flex-col gap-4">
        {!response && !loading && !error && (
          <div className="flex flex-col items-center justify-center h-full text-muted-foreground opacity-70">
            <Bot size={48} className="mb-4" />
            <p>Ask a question about your security analyses, frauds, or CVEs.</p>
          </div>
        )}
        
        {loading && <div className="text-primary animate-pulse">Analyzing context...</div>}
        
        {error && <div className="text-destructive font-medium">Error: {error}</div>}
        
        {response && (
          <div className="space-y-4">
            <div className="bg-muted p-4 rounded-md">
              <p className="font-semibold mb-1">Your Question:</p>
              <p>{response.prompt}</p>
            </div>
            <div className="bg-primary/10 border border-primary/20 p-4 rounded-md">
              <div className="flex items-center gap-2 mb-2 text-primary font-bold">
                <Bot size={20} />
                Sentrix AI
                {response.confidence && (
                  <span className="ml-auto text-xs px-2 py-1 bg-background rounded-full text-foreground opacity-80">
                    Confidence: {response.confidence}
                  </span>
                )}
              </div>
              <p className="whitespace-pre-wrap">{response.answer}</p>
              
              {response.sources && response.sources.length > 0 && (
                <div className="mt-4 pt-4 border-t border-primary/20 text-sm opacity-80">
                  <p className="font-semibold mb-1">Sources Context Used:</p>
                  <ul className="list-disc pl-5">
                    {response.sources.map((s: string, idx: number) => <li key={idx}>{s}</li>)}
                  </ul>
                </div>
              )}
            </div>
          </div>
        )}
      </div>

      <div className="relative mt-auto">
        <input 
          type="text" 
          value={prompt}
          onChange={e => setPrompt(e.target.value)}
          onKeyDown={e => e.key === 'Enter' && handleAsk()}
          placeholder="e.g. Why was transaction txn-123 considered risky?" 
          className="w-full bg-background border border-input rounded-full py-4 pl-6 pr-14 focus:outline-none focus:ring-2 focus:ring-primary"
          disabled={loading}
        />
        <button 
          onClick={handleAsk}
          disabled={loading || !prompt.trim()}
          className="absolute right-2 top-2 p-2 bg-primary text-primary-foreground rounded-full hover:bg-primary/90 disabled:opacity-50"
        >
          <Send size={20} />
        </button>
      </div>
    </div>
  );
};

export default AIAssistant;
