import { ShieldAlert, Activity, FileWarning, Search, Crosshair } from 'lucide-react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { EmptyState } from '../components/ui/empty-state';
import { useEffect, useState } from 'react';
import axios from 'axios';
import type { SecurityPostureDto } from '@/api/posture';
import type { SecuritySearchResultDto } from '@/api/search';

interface AnalystWorkspaceDto {
  postureSummary: SecurityPostureDto;
  priorityAlerts: SecuritySearchResultDto[];
  activeIncidents: SecuritySearchResultDto[];
  openInvestigations: SecuritySearchResultDto[];
  pendingActions: SecuritySearchResultDto[];
}

const AnalystWorkspace = () => {
  const [workspaceData, setWorkspaceData] = useState<AnalystWorkspaceDto | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchWorkspace = async () => {
      try {
        const response = await axios.get('/api/v1/analyst/workspace');
        setWorkspaceData(response.data);
      } catch (err: any) {
        console.error(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchWorkspace();
  }, []);

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-1">
        <h1 className="text-3xl font-bold tracking-tight text-foreground flex items-center gap-3">
          <Crosshair className="h-8 w-8 text-primary" />
          Analyst Workspace
        </h1>
        <p className="text-muted-foreground">Unified security operations center for cross-domain investigation.</p>
      </div>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <Card className="bg-primary/5 border-primary/20">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Security Posture</CardTitle>
            <ShieldAlert className="h-4 w-4 text-primary" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{loading ? '--' : `${workspaceData?.postureSummary?.overallScore || 0}/100`}</div>
            <p className="text-xs text-muted-foreground">Overall Risk Rating</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Priority Alerts</CardTitle>
            <Activity className="h-4 w-4 text-orange-500" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{loading ? '--' : workspaceData?.priorityAlerts.length || 0}</div>
            <p className="text-xs text-muted-foreground">Requires immediate review</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Active Incidents</CardTitle>
            <FileWarning className="h-4 w-4 text-red-500" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold text-red-500">{loading ? '--' : workspaceData?.activeIncidents.length || 0}</div>
            <p className="text-xs text-muted-foreground">Open security incidents</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Pending Actions</CardTitle>
            <Search className="h-4 w-4 text-blue-500" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{loading ? '--' : workspaceData?.pendingActions.length || 0}</div>
            <p className="text-xs text-muted-foreground">Awaiting human approval</p>
          </CardContent>
        </Card>
      </div>

      <div className="grid gap-4 md:grid-cols-2">
        <Card>
          <CardHeader>
            <CardTitle>Pending Security Actions</CardTitle>
            <CardDescription>Actions requiring human authorization.</CardDescription>
          </CardHeader>
          <CardContent>
            {(!workspaceData?.pendingActions || workspaceData.pendingActions.length === 0) ? (
              <EmptyState 
                icon={ShieldAlert} 
                title="No Pending Actions" 
                description="There are currently no security actions awaiting human approval." 
              />
            ) : (
              <div className="space-y-4">
                {workspaceData.pendingActions.map((action, i) => (
                  <div key={i} className="p-4 rounded-lg border border-border bg-card">
                    <div className="flex justify-between">
                      <span className="font-semibold text-sm">{action.title}</span>
                      <span className="text-xs text-blue-500 font-medium">{action.status}</span>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </CardContent>
        </Card>
        
        <Card>
          <CardHeader>
            <CardTitle>Active Investigations</CardTitle>
            <CardDescription>Ongoing deep-dive analysis cases.</CardDescription>
          </CardHeader>
          <CardContent>
            {(!workspaceData?.openInvestigations || workspaceData.openInvestigations.length === 0) ? (
              <EmptyState 
                icon={Search} 
                title="No Open Investigations" 
                description="There are no active security investigations." 
              />
            ) : (
              <div className="space-y-4">
                {workspaceData.openInvestigations.map((inv, i) => (
                  <div key={i} className="p-4 rounded-lg border border-border bg-card">
                    <div className="flex justify-between">
                      <span className="font-semibold text-sm">{inv.title}</span>
                      <span className="text-xs text-yellow-500 font-medium">{inv.status}</span>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default AnalystWorkspace;
