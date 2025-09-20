import type { EnfantDTO, UserDTO } from "./user";
import type { VaccinDTO } from "./vaccin";



// Interface pour un rendez-vous
export interface AppointmentDTO {
  id: string | null;
  enfantId: string | null;
  parentId: string | null;
  vaccinId: string | null;
  dateRendezVous: string;
  heureRendezVous: string;
  centreVaccination: string;
  statut: StatutRendezVousEnum;
  notes: string;
  rappelEnvoye: boolean;
  // Champs enrichis
  enfant?: EnfantDTO | null;
  parent?: UserDTO | null;
  vaccin?: VaccinDTO | null;
}

// Enum pour le statut du rendez-vous
export const StatutRendezVous = {
  PLANIFIE: 'PLANIFIE',
  CONFIRME: 'CONFIRME',
  REPORTE: 'REPORTE',
  ANNULE: 'ANNULE',
  REALISE: 'REALISE'
} as const;

export type StatutRendezVousEnum = typeof StatutRendezVous[keyof typeof StatutRendezVous];



// Pour les opérations de création (sans id)]
export type AppointmentCreateDTO = Omit<AppointmentDTO, 'id' | 'enfant' | 'parent' | 'vaccin' | 'rappelEnvoye'>;

// Types spécialisés pour les formulaires de sauvegarde
export type AppointmentUpdateDTO = Partial<AppointmentCreateDTO>;