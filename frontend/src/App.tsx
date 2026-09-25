import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import DashboardLayout from './layouts/DashboardLayout';
import Landing from './pages/Landing';
import Dashboard from './pages/Dashboard';
import FraudAnalysis from './pages/FraudAnalysis';
import EventLogs from './pages/EventLogs';
import CVEIntelligence from './pages/CVEIntelligence';
import AIAssistant from './pages/AIAssistant';
import Reports from './pages/Reports';
import History from './pages/History';
import WindowsEvents from './pages/WindowsEvents';
import UpiSecurity from './pages/UpiSecurity';
import Incidents from './pages/Incidents';
import IncidentDetail from './pages/IncidentDetail';
import Investigations from './pages/Investigations';
import InvestigationDetail from './pages/InvestigationDetail';
import Actions from './pages/Actions';
import ActionDetail from './pages/ActionDetail';
import AnalystWorkspace from './pages/AnalystWorkspace';
import { Toaster } from '@/components/ui/sonner';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Landing />} />
        <Route element={<DashboardLayout />}>
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/investigations" element={<Investigations />} />
          <Route path="/investigations/:id" element={<InvestigationDetail />} />
          <Route path="/fraud" element={<FraudAnalysis />} />
          <Route path="/upi-security" element={<UpiSecurity />} />
          <Route path="/windows-events" element={<WindowsEvents />} />
          <Route path="/event-logs" element={<EventLogs />} />
          <Route path="/cves" element={<CVEIntelligence />} />
          <Route path="/incidents" element={<Incidents />} />
          <Route path="/incidents/:id" element={<IncidentDetail />} />
          <Route path="/actions" element={<Actions />} />
          <Route path="/actions/:id" element={<ActionDetail />} />
          <Route path="/security-operations" element={<AnalystWorkspace />} />
          <Route path="/assistant" element={<AIAssistant />} />
          <Route path="/reports" element={<Reports />} />
          <Route path="/history" element={<History />} />
        </Route>
      </Routes>
      <Toaster />
    </Router>
  );
}

export default App;
