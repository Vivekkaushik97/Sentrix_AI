import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { actionApi } from '../api/actions';
import { ActionStatus } from '../types/actions';
import type { SecurityAction, ActionAuditEntry } from '../types/actions';
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { ShieldAlert, ArrowLeft, CheckCircle, XCircle, Play, FileText, AlertTriangle } from 'lucide-react';
import { toast } from 'sonner';

const ActionDetail = () => {
    const { id } = useParams<{ id: string }>();
    const [action, setAction] = useState<SecurityAction | null>(null);
    const [auditLog, setAuditLog] = useState<ActionAuditEntry[]>([]);
    const [loading, setLoading] = useState(true);

    const fetchData = async () => {
        if (!id) return;
        try {
            setLoading(true);
            const [actionData, auditData] = await Promise.all([
                actionApi.getAction(id),
                actionApi.getAuditTrail(id)
            ]);
            setAction(actionData);
            setAuditLog(auditData);
        } catch (error) {
            console.error("Error fetching action", error);
            toast.error("Failed to load action details.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchData();
    }, [id]);

    const handleRequestApproval = async () => {
        if (!id) return;
        try {
            await actionApi.requestApproval(id);
            toast.success("Approval requested successfully");
            fetchData();
        } catch (e: any) {
            toast.error(e.response?.data || "Failed to request approval");
        }
    };

    const handleApprove = async () => {
        if (!id) return;
        try {
            await actionApi.approveAction(id, { reason: "Approved by Analyst" });
            toast.success("Action approved");
            fetchData();
        } catch (e: any) {
            toast.error(e.response?.data || "Failed to approve action");
        }
    };

    const handleReject = async () => {
        if (!id) return;
        try {
            const rejectReason = prompt("Reason for rejection:");
            if (!rejectReason) return;
            await actionApi.rejectAction(id, { reason: rejectReason });
            toast.success("Action rejected");
            fetchData();
        } catch (e: any) {
            toast.error(e.response?.data || "Failed to reject action");
        }
    };

    const handleExecute = async () => {
        if (!id) return;
        if (!confirm("Are you sure you want to execute this action? It is running in a controlled mode.")) return;
        try {
            await actionApi.executeAction(id);
            toast.success("Execution completed");
            fetchData();
        } catch (e: any) {
            toast.error(e.response?.data || "Failed to execute action");
        }
    };

    if (loading) return <div className="p-8">Loading...</div>;
    if (!action) return <div className="p-8">Action not found</div>;

    const isPendingApproval = action.status === ActionStatus.PENDING_APPROVAL;
    const isApproved = action.status === ActionStatus.APPROVED;
    const isProposed = action.status === ActionStatus.PROPOSED;

    return (
        <div className="space-y-6 max-w-5xl mx-auto">
            <div className="flex items-center justify-between">
                <div className="flex items-center gap-4">
                    <Link to="/actions">
                        <Button variant="ghost" size="icon">
                            <ArrowLeft className="h-5 w-5" />
                        </Button>
                    </Link>
                    <div>
                        <h1 className="text-3xl font-bold tracking-tight flex items-center gap-2">
                            <ShieldAlert className="h-6 w-6 text-primary" />
                            {action.actionType}
                        </h1>
                        <p className="text-muted-foreground text-sm mt-1">ID: {action.id}</p>
                    </div>
                </div>
                <div className="flex items-center gap-2">
                    <span className="px-3 py-1 bg-secondary text-secondary-foreground rounded-full text-sm font-medium">
                        {action.status}
                    </span>
                    <span className="px-3 py-1 bg-primary/10 text-primary rounded-full text-sm font-medium border border-primary/20">
                        {action.priority}
                    </span>
                </div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                <div className="md:col-span-2 space-y-6">
                    <Card>
                        <CardHeader>
                            <CardTitle>Action Details</CardTitle>
                        </CardHeader>
                        <CardContent className="space-y-4">
                            <div>
                                <h4 className="text-sm font-medium text-muted-foreground mb-1">Reason</h4>
                                <p className="text-sm bg-secondary/30 p-3 rounded-md">{action.reason || "No reason provided."}</p>
                            </div>
                            
                            <div className="grid grid-cols-2 gap-4 pt-2">
                                {action.targetReference && (
                                    <div>
                                        <h4 className="text-sm font-medium text-muted-foreground mb-1">Target Reference</h4>
                                        <p className="text-sm font-mono">{action.targetReference}</p>
                                    </div>
                                )}
                                <div>
                                    <h4 className="text-sm font-medium text-muted-foreground mb-1">Created At</h4>
                                    <p className="text-sm">{new Date(action.createdAt).toLocaleString()}</p>
                                </div>
                            </div>
                            
                            {(action.investigationId || action.incidentId) && (
                                <div className="pt-4 border-t border-border flex gap-4">
                                    {action.investigationId && (
                                        <Link to={`/investigations/${action.investigationId}`}>
                                            <Button variant="outline" size="sm" className="gap-2">
                                                <FileText className="h-4 w-4" /> View Investigation
                                            </Button>
                                        </Link>
                                    )}
                                    {action.incidentId && (
                                        <Link to={`/incidents/${action.incidentId}`}>
                                            <Button variant="outline" size="sm" className="gap-2">
                                                <AlertTriangle className="h-4 w-4" /> View Incident
                                            </Button>
                                        </Link>
                                    )}
                                </div>
                            )}
                        </CardContent>
                    </Card>

                    <Card>
                        <CardHeader>
                            <CardTitle>Audit Trail</CardTitle>
                        </CardHeader>
                        <CardContent>
                            {auditLog.length === 0 ? (
                                <p className="text-sm text-muted-foreground">No audit events found.</p>
                            ) : (
                                <div className="space-y-4">
                                    {auditLog.map((log) => (
                                        <div key={log.id} className="flex gap-4 p-3 border border-border/50 rounded-lg bg-card hover:bg-secondary/10 transition-colors">
                                            <div className="flex-1">
                                                <div className="flex items-center justify-between mb-1">
                                                    <span className="font-semibold text-sm">{log.eventType}</span>
                                                    <span className="text-xs text-muted-foreground">{new Date(log.createdAt).toLocaleString()}</span>
                                                </div>
                                                <p className="text-sm text-muted-foreground">{log.reason}</p>
                                                <div className="text-xs text-primary mt-2 font-mono opacity-80">Actor: {log.actor}</div>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            )}
                        </CardContent>
                    </Card>
                </div>

                <div className="space-y-6">
                    <Card className="border-primary/20 bg-primary/5">
                        <CardHeader>
                            <CardTitle>Action Controls</CardTitle>
                        </CardHeader>
                        <CardContent className="space-y-4 flex flex-col">
                            {isProposed && (
                                <Button className="w-full" onClick={handleRequestApproval}>
                                    Request Approval
                                </Button>
                            )}
                            
                            {isPendingApproval && (
                                <>
                                    <Button className="w-full bg-green-600 hover:bg-green-700 text-white" onClick={handleApprove}>
                                        <CheckCircle className="mr-2 h-4 w-4" /> Approve Action
                                    </Button>
                                    <Button variant="destructive" className="w-full" onClick={handleReject}>
                                        <XCircle className="mr-2 h-4 w-4" /> Reject Action
                                    </Button>
                                </>
                            )}
                            
                            {isApproved && (
                                <Button className="w-full bg-indigo-600 hover:bg-indigo-700 text-white" onClick={handleExecute}>
                                    <Play className="mr-2 h-4 w-4" /> Execute Action
                                </Button>
                            )}

                            {!isProposed && !isPendingApproval && !isApproved && (
                                <p className="text-sm text-center text-muted-foreground italic">
                                    No operations available in current state ({action.status}).
                                </p>
                            )}
                        </CardContent>
                    </Card>
                </div>
            </div>
        </div>
    );
};

export default ActionDetail;
