import { AppHeader } from '@/components/shared/app-header';
import { AppSidebar } from '@/components/shared/app-sidebar';
import { SidebarProvider } from '@/components/ui/sidebar';
import { Home, Users, Heart, Calendar, MapPin, Package, BarChart3, FileText, Settings, Inbox } from 'lucide-react';
import { Outlet } from 'react-router-dom';

export default function PublicLayout() {
    // Menu sections.
    const sections = [
        { 
            title: "TABLEAU DE BORD", 
            items: [
            { label: "Accueil", icon: Home, path: "/medecin/accueil" },
            ]
        },
        {
            title: "PATIENTS & VACCINATIONS", 
            items: [
            { icon: Users, label: "Patients", path: "/medecin/patients" },
            { icon: Heart, label: "Vaccinations", path: "/medecin/vaccinations" },
            { icon: Calendar, label: "Calendrier", path: "/medecin/calendrier" }
            ]
        },
        {
            title: "GESTION",
            items: [
            { icon: MapPin, label: "Centres", path: "/medecin/centres" },
            { icon: Package, label: "Stock vaccins", path: "/medecin/stocks" }
            ]
        },
        {
            title: "STATISTIQUES & RAPPORTS",
            items: [
            { icon: BarChart3, label: "Statistiques", path: "/medecin/statistiques" },
            { icon: FileText, label: "Rapports", path: "/medecin/rapports" }
            ]
        },
        {
            title: "PARAMÈTRES",
            items: [
            { icon: Settings, label: "Paramètres", path: "/medecin/parametres" },
            { icon: Inbox, label: "Inbox", path: "/medecin/messages" },
            ]
        }
    ];

  return (
    <SidebarProvider>
      <div className="flex min-h-screen w-full">
        <AppSidebar itemsProp={sections} pageTitle="VacciMed" />
        <div className="flex flex-1 flex-col">
          <AppHeader />
          <main className="flex-1 overflow-y-auto">
            <div className="container mx-auto p-4 md:p-6 lg:p-8">
              <Outlet />
            </div>
          </main>
        </div>
      </div>
    </SidebarProvider>
  );
}