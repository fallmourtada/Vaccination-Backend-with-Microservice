import type { AuthToken } from "@/types";

  export class AuthService {
  
    static getAuthToken(): string | null {
        try {
        const tokenData = localStorage.getItem('authToken');
        if (!tokenData) return null;
        
        const parsed: AuthToken = JSON.parse(tokenData);
        
        // Vérifier l'expiration
        if (Date.now() > parsed.expiresAt) {
            this.removeAuthToken();
            return null;
        }
        
        return parsed.token;
        } catch {
            AuthService.removeAuthToken();
            return null;
        }
    }

    static removeAuthToken(): void {
        localStorage.removeItem('authToken');
    }

    static isAuthenticated(): boolean {
        return !!this.getAuthToken();
    }

    static handleUnauthorized(): void {
        AuthService.removeAuthToken();
        window.location.href = '/login';
    }


  }