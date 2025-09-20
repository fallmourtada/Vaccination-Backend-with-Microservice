import {
    Avatar,
    AvatarFallback,
    AvatarImage,
} from "@/components/ui/avatar";
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { SidebarTrigger } from "@/components/ui/sidebar";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { 
    User, 
    Settings, 
    LogOut, 
    Bell, 
    Shield
} from "lucide-react";
import { useTheme } from "./theme-provider";
import { ModeToggle } from "./mode-toggle";
import AppBreadcrumb from "./app-breadcrumb";


export function AppHeader() {
    const { theme } = useTheme();

    return (
        <header className={`w-full sticky top-0 bg-background/80 p-1 backdrop-blur-sm ${theme !== "light" ? "border-b" : ""} shadow-xs z-50`}>
            <div className="flex items-center justify-between px-4 py-3 lg:px-6">
                {/* Section gauche */}
                <div className="flex items-center gap-2 lg:gap-4 min-w-0 flex-1">
                    <SidebarTrigger className="cursor-pointer flex-shrink-0 -ml-2 sm:ml-3 md:-ml-4" />
                    {/* Breadcrumb - Masqué sur très petits écrans, visible à partir de sm */}
                    <div className="min-w-0 flex-1 overflow-hidden">
                        <AppBreadcrumb />
                    </div>
                </div>

                {/* Section droite */}
                <div className="flex items-center gap-2 lg:gap-3 flex-shrink-0">
                {/* Notifications optionnelles */}
                <Button variant="ghost" size="sm" className="relative h-9 w-9 cursor-pointer">
                    <Bell className="h-4 w-4" />
                    <Badge 
                        variant="destructive" 
                        className="absolute -top-1 -right-1 h-5 w-5 p-0 flex items-center justify-center text-xs"
                    >
                        3
                    </Badge>
                </Button>

                {/* Mode Toggle intégré dans un dropdown professionnel */}
                <ModeToggle />

                {/* Séparateur visuel */}
                <div className="h-6 w-px bg-border" />

                {/* Menu utilisateur amélioré */}
                <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                        <Button variant="ghost" className="relative h-10 px-2 rounded-full cursor-pointer">
                            <div className="flex items-center gap-2">
                                <Avatar className="h-8 w-8">
                                    <AvatarImage src="https://github.com/shadcn.png" alt="Dr. Sarah Johnson" />
                                    <AvatarFallback className="bg-gradient-to-br from-primary to-primary/80 text-primary-foreground font-semibold text-sm">
                                        SJ
                                    </AvatarFallback>
                                </Avatar>
                                {/* Nom affiché optionnel sur desktop */}
                                <div className="hidden md:flex flex-col items-start">
                                    <span className="text-sm font-medium">Dr. Johnson</span>
                                    <span className="text-xs text-muted-foreground">En ligne</span>
                                </div>
                            </div>
                        </Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent className="w-64 p-2" align="end" forceMount>
                        <DropdownMenuLabel className="font-normal p-3">
                            <div className="flex items-center gap-3">
                                <Avatar className="h-12 w-12">
                                    <AvatarImage src="https://github.com/shadcn.png" alt="Dr. Sarah Johnson" />
                                    <AvatarFallback className="bg-gradient-to-br from-primary to-primary/80 text-primary-foreground font-semibold text-lg">
                                        SJ
                                    </AvatarFallback>
                                </Avatar>
                                <div className="flex flex-col">
                                    <p className="text-sm font-semibold">Dr. Sarah Johnson</p>
                                    <p className="text-xs text-muted-foreground">sarah.johnson@hospital.com</p>
                                    <Badge variant="secondary" className="w-fit mt-1 text-xs">
                                        Cardiologue
                                    </Badge>
                                </div>
                            </div>
                        </DropdownMenuLabel>
                        
                        <DropdownMenuSeparator />
                        
                        <DropdownMenuItem className="cursor-pointer p-3">
                            <User className="mr-3 h-4 w-4" />
                            <div className="flex flex-col">
                                <span className="text-sm font-medium">Mon Profil</span>
                                <span className="text-xs text-muted-foreground">Informations personnelles</span>
                            </div>
                        </DropdownMenuItem>
                        
                        <DropdownMenuItem className="cursor-pointer p-3">
                            <Settings className="mr-3 h-4 w-4" />
                            <div className="flex flex-col">
                                <span className="text-sm font-medium">Paramètres</span>
                                <span className="text-xs text-muted-foreground">Préférences et configuration</span>
                            </div>
                        </DropdownMenuItem>
                        
                        <DropdownMenuItem className="cursor-pointer p-3">
                            <Shield className="mr-3 h-4 w-4" />
                            <div className="flex flex-col">
                                <span className="text-sm font-medium">Sécurité</span>
                                <span className="text-xs text-muted-foreground">Authentification et accès</span>
                            </div>
                        </DropdownMenuItem>
                        
                        <DropdownMenuSeparator />
                        
                        <DropdownMenuItem className="cursor-pointer text-red-600 focus:text-red-600 focus:bg-red-50 dark:focus:bg-red-950/20 p-3">
                            <LogOut className="mr-3 h-4 w-4" />
                            <div className="flex flex-col">
                                <span className="text-sm font-medium">Se déconnecter</span>
                                <span className="text-xs opacity-70">Fermer la session</span>
                            </div>
                        </DropdownMenuItem>
                    </DropdownMenuContent>
                </DropdownMenu>
                </div>
            </div>
        </header>
    );
}