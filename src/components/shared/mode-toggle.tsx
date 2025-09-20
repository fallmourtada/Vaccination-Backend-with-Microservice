import { Moon, Sun, Monitor, Palette, CheckCircle2 } from "lucide-react";
import { useTheme } from "./theme-provider";

import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";


export function ModeToggle() {
  const { theme, setTheme } = useTheme();

  return (
    <DropdownMenu>
        <DropdownMenuTrigger asChild>
            <Button variant="ghost" size="sm" className="h-9 w-9 cursor-pointer">
                {theme === "light" && <Sun className="h-4 w-4 cursor-pointer" />}
                {theme === "dark" && <Moon className="h-4 w-4 cursor-pointer" />}
                {theme === "system" && <Monitor className="h-4 w-4 cursor-pointer" />}
            </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent align="end" className="w-48">
            <DropdownMenuLabel className="flex items-center gap-2">
                <Palette className="h-4 w-4" />
                Mode d'affichage
            </DropdownMenuLabel>
            <DropdownMenuSeparator />
                        
            <DropdownMenuItem 
                onClick={() => setTheme("light")}
                className="cursor-pointer"
            >
                <Sun className="mr-3 h-4 w-4" />
                <div className="flex flex-col flex-1">
                    <span className="text-sm">Clair</span>
                    <span className="text-xs text-muted-foreground">Mode jour</span>
                </div>
                {theme === "light" && <CheckCircle2 className="h-4 w-4 text-primary" />}
            </DropdownMenuItem>
                        
            <DropdownMenuItem 
                onClick={() => setTheme("dark")}
                className="cursor-pointer"
            >
                <Moon className="mr-3 h-4 w-4" />
                <div className="flex flex-col flex-1">
                    <span className="text-sm">Sombre</span>
                    <span className="text-xs text-muted-foreground">Mode nuit</span>
                </div>
                {theme === "dark" && <CheckCircle2 className="h-4 w-4 text-primary" />}
            </DropdownMenuItem>
                        
            <DropdownMenuItem 
                onClick={() => setTheme("system")}
                className="cursor-pointer"
            >
                <Monitor className="mr-3 h-4 w-4" />
                <div className="flex flex-col flex-1">
                    <span className="text-sm">Système</span>
                    <span className="text-xs text-muted-foreground">Auto</span>
                </div>
                {theme === "system" && <CheckCircle2 className="h-4 w-4 text-primary" />}
            </DropdownMenuItem>
        </DropdownMenuContent>
    </DropdownMenu>
  );
}