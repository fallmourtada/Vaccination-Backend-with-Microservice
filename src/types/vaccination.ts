import type { EnfantDTO } from "./user";
import type { VaccinDTO } from "./vaccin";



// Interface pour une vaccination (acte vaccinal)
export interface VaccinationDTO {
  id: string | null;
  enfantId: string | null;
  vaccinId: string | null;
  dateVaccination: string;
  centreVaccination: string;
  lotVaccin: string;
  professionnelSante: string;
  reactions: string;
  prochainRappel: string | null;
  // Champs enrichis
  enfant?: EnfantDTO | null;
  vaccin?: VaccinDTO | null;
}



// Pour les opérations de création (sans id)
export type VaccinationCreateDTO = Omit<VaccinationDTO, 'id' | 'enfant' | 'vaccin'>;

// Types spécialisés pour les formulaires de sauvegarde
export type VaccinationUpdateDTO = Partial<VaccinationCreateDTO>;