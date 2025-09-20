import { BarChart3, Bell, Calendar, ChevronUp, CreditCard, FileText, Heart, Home, Inbox, LogOut, MapPin, Package, Settings, Shield, User, Users } from "lucide-react"
import { Link, useLocation } from 'react-router-dom';
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  useSidebar,
} from "@/components/ui/sidebar"
import { Separator } from "@/components/ui/separator";
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from "../ui/dropdown-menu";
import { Avatar, AvatarFallback, AvatarImage } from "../ui/avatar";
import { AppLogo } from "@/utils/common";



// Menu items.
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
      { icon: Inbox, label: "Messages", path: "/medecin/messages" },
    ]
  }
]


export function AppSidebar({itemsProp, pageTitle}: {itemsProp?: typeof sections, pageTitle:string}) {
    const { state } = useSidebar();
    const isCollapsed = state === "collapsed";

    const location = useLocation();
    const isActive: (path: string) => boolean = (path) => location.pathname === path;

  return (
    <Sidebar collapsible="icon">

    <SidebarHeader className={` ${!isCollapsed ? "p-4" : "p-3"}`}>
        <div className="flex items-center gap-3 pt-2">
          <AppLogo className={`flex-shrink-0 ${!isCollapsed ? "w-8 h-8" : "w-6 h-6"}`} />
          {!isCollapsed && (
            <div className="flex flex-col align-items-center pt-1">
              <h2 className="text-xl font-bold text-sidebar-foreground">{pageTitle}</h2>
              {/* <span className="text-xs text-sidebar-foreground/60">v1.0.0</span> */}
            </div>
          )}
        </div>
      </SidebarHeader>
      {/* <div className="border-t border-b border-border" /> */}
      {!isCollapsed && <Separator />}
      <div className={`h-full overflow-y-auto ${!isCollapsed ? 'p-2' : ''}`}>
        <SidebarContent>
          { (itemsProp ?? sections).map((section) => (
            <SidebarGroup key={section.title}>
              {/* Titre de la section */}
              <SidebarGroupLabel>{section.title}</SidebarGroupLabel>

              <SidebarGroupContent>
                <SidebarMenu>
                  {section.items.map((item) => (
                    <SidebarMenuItem key={item.path} className="cursor-pointer">
                      <SidebarMenuButton asChild>
                        <Link to={item.path} className={`flex items-center gap-3 w-full ${isActive(item.path) ? 'bg-sidebar-accent text-sidebar-accent-foreground font-medium ' : 'text-sidebar-foreground hover:bg-sidebar-hover hover:text-sidebar-foreground'} px-3 py-2 rounded-lg transition-colors duration-200`}>
                          <item.icon className="w-4 h-4" />
                          <span>{item.label}</span>
                        </Link>
                      </SidebarMenuButton>
                    </SidebarMenuItem>
                  ))}
                </SidebarMenu>
              </SidebarGroupContent>
            </SidebarGroup>
          ))}
        </SidebarContent>
      </div>

    <div className="bg-inherit md:shadow-inner hover:none pb-3">
      <SidebarFooter>
        <SidebarMenu>
          <SidebarMenuItem>
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <SidebarMenuButton className="data-[state=open]:bg-sidebar-accent data-[state=open]:text-sidebar-accent-foreground cursor-pointer">
                  <Avatar className={`${!isCollapsed ? 'h-8 w-8' : 'h-6 w-6'}`}>
                    <AvatarImage src="https://github.com/shadcn.png" alt="John Doe" />
                    <AvatarFallback className="bg-gradient-to-br from-indigo-500 to-purple-600 text-white font-semibold">
                      JD
                    </AvatarFallback>
                  </Avatar>
                  {!isCollapsed && (
                    <div className="flex flex-col text-left">
                      <span className="text-sm font-medium">John Doe</span>
                      <span className="text-xs text-sidebar-foreground/60">john@myapp.com</span>
                    </div>
                  )}
                  <ChevronUp className="ml-auto transition-transform data-[state=open]:rotate-180" />
                </SidebarMenuButton>
              </DropdownMenuTrigger>
              <DropdownMenuContent
                side="top"
                align={isCollapsed ? "center" : "start"}
                className="w-64 p-2"
              >
                <DropdownMenuLabel className="font-normal">
                  <div className="flex items-center gap-3 p-2">
                    <Avatar className="h-12 w-12">
                      <AvatarImage src="https://github.com/shadcn.png" alt="John Doe" />
                      <AvatarFallback className="bg-gradient-to-br from-indigo-500 to-purple-600 text-white font-semibold text-lg">
                        JD
                      </AvatarFallback>
                    </Avatar>
                    <div className="flex flex-col">
                      <p className="text-sm font-semibold">John Doe</p>
                      <p className="text-xs text-muted-foreground">john@myapp.com</p>
                      <p className="text-xs text-muted-foreground">Administrateur</p>
                    </div>
                  </div>
                </DropdownMenuLabel>
                
                <DropdownMenuSeparator />
                
                <DropdownMenuItem className="cursor-pointer">
                  <User className="mr-3 h-4 w-4" />
                  <div className="flex flex-col">
                    <span className="text-sm">Mon Profil</span>
                    <span className="text-xs text-muted-foreground">Gérer mes informations</span>
                  </div>
                </DropdownMenuItem>
                
                <DropdownMenuItem className="cursor-pointer">
                  <Settings className="mr-3 h-4 w-4" />
                  <div className="flex flex-col">
                    <span className="text-sm">Paramètres</span>
                    <span className="text-xs text-muted-foreground">Préférences et configuration</span>
                  </div>
                </DropdownMenuItem>
                
                <DropdownMenuItem className="cursor-pointer">
                  <Bell className="mr-3 h-4 w-4" />
                  <div className="flex flex-col">
                    <span className="text-sm">Notifications</span>
                    <span className="text-xs text-muted-foreground">3 nouvelles notifications</span>
                  </div>
                </DropdownMenuItem>
                
                <DropdownMenuItem className="cursor-pointer">
                  <CreditCard className="mr-3 h-4 w-4" />
                  <div className="flex flex-col">
                    <span className="text-sm">Facturation</span>
                    <span className="text-xs text-muted-foreground">Plan Pro actif</span>
                  </div>
                </DropdownMenuItem>
                
                <DropdownMenuItem className="cursor-pointer">
                  <Shield className="mr-3 h-4 w-4" />
                  <div className="flex flex-col">
                    <span className="text-sm">Sécurité</span>
                    <span className="text-xs text-muted-foreground">Authentification 2FA</span>
                  </div>
                </DropdownMenuItem>
                
                <DropdownMenuSeparator />
                
                <DropdownMenuItem className="cursor-pointer text-red-600 focus:text-red-600 focus:bg-red-50 dark:focus:bg-red-950/20">
                  <LogOut className="mr-3 h-4 w-4" />
                  <div className="flex flex-col">
                    <span className="text-sm font-medium">Se déconnecter</span>
                    <span className="text-xs opacity-70">Fermer la session</span>
                  </div>
                </DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarFooter>
      </div>
    </Sidebar>
  )
}