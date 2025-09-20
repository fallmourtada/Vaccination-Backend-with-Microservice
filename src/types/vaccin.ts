// Interface pour un vaccin
export interface VaccinDTO {
  id: string | null;
  nom: string;
  description: string;
  maladiesCiblees: string[];
  ageMinimumMois: number;
  ageMaximumMois: number | null;
  intervalleDoses: number;
  nombreDoses: number;
  type: TypeVaccinEnum;
  obligatoire: boolean;
  active: boolean;
}

// Enum pour le type de vaccin
export const TypeVaccin = {
  OBLIGATOIRE: 'OBLIGATOIRE',
  RECOMMANDE: 'RECOMMANDE',
  OPTIONNEL: 'OPTIONNEL'
} as const;

export type TypeVaccinEnum = typeof TypeVaccin[keyof typeof TypeVaccin];


// Pour les opérations de création (sans id)
export type VaccinCreateDTO = Omit<VaccinDTO, 'id'>;

// Types spécialisés pour les formulaires de sauvegarde
export type VaccinSaveDTO = Omit<VaccinCreateDTO, 'active'>;

// Types pour les opérations de mise à jour
export type VaccinUpdateDTO = Partial<VaccinCreateDTO>;