// Interface pour les credentials de connexion
export interface AuthCredentials {
  readonly email: string;
  readonly password: string;
}

// Interface pour les données d'authentification
export interface AuthToken {
  readonly token: string;
  readonly expiresAt: number;
}