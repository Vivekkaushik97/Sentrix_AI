import { useEffect, useState } from 'react';
import { actionApi } from '../api/actions';
import { ActionStatus } from '../types/actions';
import type { SecurityAction, ActionStatusType } from '../types/actions';
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { ShieldAlert, Plus } from 'lucide-react';
import { Link } from 'react-router-dom';

const Actions = () => {
    const [actions, setActions] = useState<SecurityAction[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchActions = async () => {
            try {
                const data = await actionApi.listActions();
                setActions(data);
            } catch (error) {
                console.error("Error fetching actions", error);
            } finally {
                setLoading(false);
            }
        };
        fetchActions();
    }, []);

    const getStatusColor = (status: ActionStatusType) => {
        switch (status) {
            case ActionStatus.PROPOSED: return 'bg-gray-500/20 text-gray-400';
            case ActionStatus.PENDING_APPROVAL: return 'bg-yellow-500/20 text-yellow-400';
            case ActionStatus.APPROVED: return 'bg-blue-500/20 text-blue-400';
            case ActionStatus.EXECUTING: return 'bg-indigo-500/20 text-indigo-400';
            case ActionStatus.COMPLETED: return 'bg-green-500/20 text-green-400';
            case ActionStatus.FAILED: 
            case ActionStatus.REJECTED:
                return 'bg-red-500/20 text-red-400';
            default: return 'bg-gray-500/20 text-gray-400';
        }
    };

    if (loading) {
        return <div className="p-8">Loading actions...</div>;
    }

    return (
        <div className="space-y-6">
            <div className="flex items-center justify-between">
                <div>
                    <h1 className="text-3xl font-bold tracking-tight">Action Center</h1>
                    <p className="text-muted-foreground mt-2">
                        Manage security actions and orchestrations.
                    </p>
                </div>
                <Button>
                    <Plus className="mr-2 h-4 w-4" /> Propose Action
                </Button>
            </div>

            {actions.length === 0 ? (
                <Card className="border-dashed bg-secondary/20">
                    <CardContent className="flex flex-col items-center justify-center p-12 text-center">
                        <ShieldAlert className="h-12 w-12 text-muted-foreground mb-4 opacity-20" />
                        <h3 className="text-lg font-medium text-foreground">No Actions Found</h3>
                        <p className="text-sm text-muted-foreground mt-2">
                            There are currently no security actions in the system.
                        </p>
                    </CardContent>
                </Card>
            ) : (
                <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
                    {actions.map((action) => (
                        <Card key={action.id} className="hover:border-primary/50 transition-colors">
                            <CardHeader className="pb-2">
                                <div className="flex justify-between items-start">
                                    <CardTitle className="text-lg font-medium flex items-center gap-2">
                                        <ShieldAlert className="h-4 w-4 text-primary" />
                                        {action.actionType}
                                    </CardTitle>
                                    <span className={`text-xs px-2 py-1 rounded-full font-medium ${getStatusColor(action.status)}`}>
                                        {action.status}
                                    </span>
                                </div>
                            </CardHeader>
                            <CardContent>
                                <p className="text-sm text-muted-foreground mb-4 line-clamp-2">
                                    {action.reason || "No reason provided."}
                                </p>
                                <div className="flex justify-between items-center text-xs text-muted-foreground">
                                    <span>Priority: {action.priority}</span>
                                    <span>{new Date(action.createdAt).toLocaleDateString()}</span>
                                </div>
                                <div className="mt-4">
                                    <Link to={`/actions/${action.id}`}>
                                        <Button variant="outline" className="w-full text-xs" size="sm">
                                            View Details
                                        </Button>
                                    </Link>
                                </div>
                            </CardContent>
                        </Card>
                    ))}
                </div>
            )}
        </div>
    );
};

export default Actions;
