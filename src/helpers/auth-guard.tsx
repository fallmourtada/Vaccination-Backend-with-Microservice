import type { ReactNode } from "react";
import services from "@/services/index.service";
import { Navigate } from "react-router-dom";



export default function AuthGuard({ children }: { children: ReactNode }) {
    const isConnected = services.authService.isAuthenticated();
    if (!isConnected) {
        return <Navigate to="/login" />;
    }
    return <>{children}</>;

}
