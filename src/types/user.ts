import type { LocaliteDTO } from "./localite";


// Interface pour un utilisateur
export interface UserDTO {
  id: string | null;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  dateNaissance: string;
  regionId: string | null;
  departementId: string | null;
  communeId: string | null;
  // Champs enrichis (non stockés en base)
  region?: LocaliteDTO | null;
  departement?: LocaliteDTO | null;
  commune?: LocaliteDTO | null;
}

// Interface pour un enfant
export interface EnfantDTO {
  id: string | null;
  nom: string;
  prenom: string;
  dateNaissance: string;
  sexe: SexeTypeEnum;
  numeroCarnet: string;
  parentId: string | null;
  regionId: string | null;
  departementId: string | null;
  communeId: string | null;
  // Champs enrichis
  region?: LocaliteDTO | null;
  departement?: LocaliteDTO | null;
  commune?: LocaliteDTO | null;
  parent?: UserDTO | null;
}

// Enum pour le sexe
export const SexeType = {
  MASCULIN: 'MASCULIN',
  FEMININ: 'FEMININ'
} as const;

export type SexeTypeEnum = typeof SexeType[keyof typeof SexeType];

// Pour les opérations de création (sans id)
export type UserCreateDTO = Omit<UserDTO, 'id' | 'region' | 'departement' | 'commune'>;
export type EnfantCreateDTO = Omit<EnfantDTO, 'id' | 'region' | 'departement' | 'commune' | 'parent'>;

// Types spécialisés pour les formulaires de sauvegarde
// export type UserSaveDTO = UserCreateDTO;
// export type EnfantSaveDTO = EnfantCreateDTO;

// Types pour les opérations de mise à jour
export type UserUpdateDTO = Partial<UserCreateDTO>;
export type EnfantUpdateDTO = Partial<EnfantCreateDTO>;
