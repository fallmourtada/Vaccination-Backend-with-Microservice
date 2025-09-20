import { Route, Routes } from 'react-router-dom';
import PublicLayout from './layout';
import Dashboard from './dashboard';
import Calendrier from './calendrier';
import Patient from './patient';
import PatientDetails from './patient-details';
import Vaccination from './vaccination';
import Centre from './centre';
import Stock from './stock';
import Statistique from './statistique';
import Rapport from './rapport';
import Parametre from './parametre';
import Message from './message';

export default function PublicRouter() {
  return (
    <Routes>
        {/* Routes publiques */}
        <Route element={<PublicLayout />}>
            {/* <Route index element={<Navigate to="/accueil" replace />} /> */}
            <Route path="accueil" element={<Dashboard />} />
            <Route path="patients" element={<Patient />} />
            <Route path="patient-details/:id" element={<PatientDetails />} />
            <Route path="vaccinations" element={<Vaccination />} />
            <Route path="calendrier" element={<Calendrier />} />
            <Route path="centres" element={<Centre />} />
            <Route path="stocks" element={<Stock />} />
            <Route path="statistiques" element={<Statistique />} />
            <Route path="rapports" element={<Rapport />} />
            <Route path="parametres" element={<Parametre />} />
            <Route path="messages" element={<Message />} />
        </Route>
    </Routes>
  );
}