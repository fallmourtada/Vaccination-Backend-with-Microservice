import type { AppointmentCreateDTO, AppointmentDTO } from "@/types";
import { buildAppointmentUrl } from "@/utils/api-config";
import { useAxiosDelete, useAxiosGet, useAxiosPost, useAxiosPut } from "@/hooks/use-api";
import { useQueryClient } from "@tanstack/react-query";


/** 
 * Clés de requête pour les appointments
 */
const APPOINTMENTS_KEYS = {
  all: ['appointments'] as const,
  lists: () => [...APPOINTMENTS_KEYS.all, 'list'] as const,
  detail: (id: string | number) => [...APPOINTMENTS_KEYS.all, 'detail', id] as const,
  byEnfant: (enfantId: string | number) => [...APPOINTMENTS_KEYS.all, 'byEnfant', enfantId] as const,
};

/**
 * Hook pour récupérer tous les appointments 
 */
export const useAllAppointments = () => {
  const url = buildAppointmentUrl('appointments');
  return useAxiosGet<AppointmentDTO[]>(
    APPOINTMENTS_KEYS.lists(),
    url
  );
};

/**
 * Hook pour récupérer les appointments d'un enfant spécifique
 */
export const useAppointmentsByEnfant = (enfantId: string | number) => {
  const url = buildAppointmentUrl('by_enfant', { enfantId: String(enfantId) });
  return useAxiosGet<AppointmentDTO[]>(
    APPOINTMENTS_KEYS.byEnfant(enfantId),
    url
  );
};

/**
 * Hook pour récupérer un appointment par son ID
 */
export const useAppointmentById = (appointmentId: string | number) => {
  const url = buildAppointmentUrl('appointments', { appointmentId: String(appointmentId) });
  return useAxiosGet<AppointmentDTO>(
    APPOINTMENTS_KEYS.detail(appointmentId),
    url
  );
};

/**
 * Hook pour créer un nouveau rendez-vous
 */
export const useCreateAppointment = () => {
  const queryClient = useQueryClient();
  const url = buildAppointmentUrl('create');
  return useAxiosPost<AppointmentDTO, AppointmentCreateDTO>(
    url,
    undefined,
    {
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: APPOINTMENTS_KEYS.lists() });
      }
    }
  );
};

/**
 * Hook pour annuler un rendez-vous
 */
export const useCancelAppointment = () => {
  const queryClient = useQueryClient();
  const url = buildAppointmentUrl('appointments'); // URL de base sans l'ID

  return useAxiosDelete(
    url,
    undefined,
    {
      onSuccess: (_data: any, variables: { id: string | number }) => {
        queryClient.invalidateQueries({ queryKey: APPOINTMENTS_KEYS.lists() });
        // queryClient.invalidateQueries({ queryKey: APPOINTMENTS_KEYS.detail(variables.id) });
        console.log(`Rendez-vous ${variables.id} annulé avec succès`);
      }
    }
  );
};

/**
 * Hook pour mettre à jour un rendez-vous
 */
export const useUpdateAppointment = () => {
  const queryClient = useQueryClient();
  const url = buildAppointmentUrl('appointments'); // URL de base sans l'ID

  return useAxiosPut(
    url,
    undefined,
    {
      onSuccess: (_data: any, variables: { id: string | number }) => {
        queryClient.invalidateQueries({ queryKey: APPOINTMENTS_KEYS.lists() });
        // queryClient.invalidateQueries({ queryKey: APPOINTMENTS_KEYS.detail(variables.id) });
        console.log(`Rendez-vous ${variables.id} mis à jour avec succès`);
      }
    }
  );
};

/**
 * Hook pour supprimer un rendez-vous
 *//*
export const useDeleteAppointment = () => {
  const queryClient = useQueryClient();
  const url = buildAppointmentUrl('appointments'); // URL de base sans l'ID

  return useAxiosDelete(
    url,
    undefined,
    {
      onSuccess: (_data: any, variables: { id: string | number }) => {
        queryClient.invalidateQueries({ queryKey: APPOINTMENTS_KEYS.lists() });
        queryClient.invalidateQueries({ queryKey: APPOINTMENTS_KEYS.detail(variables.id) });
        console.log(`Rendez-vous ${variables.id} supprimé avec succès`);
      }
    }
  );
}
 */