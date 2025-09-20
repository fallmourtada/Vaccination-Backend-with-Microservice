import { 
  useAxiosGet, 
  useAxiosGetById, 
  useAxiosPost,
  useAxiosPut,
  useAxiosDelete 
} from "@/hooks/use-api";
import type { 
  VaccinationDTO, 
  VaccinationCreateDTO, 
  VaccinationUpdateDTO,
} from "@/types";
import { buildVaccinationUrl } from "@/utils/api-config";
import { useQueryClient } from '@tanstack/react-query';

/**
 * Clés de cache pour les vaccinations
 */
const VACCINATIONS_KEYS = {
  all: ['vaccinations'] as const,
  lists: () => [...VACCINATIONS_KEYS.all, 'list'] as const,
  byEnfant: (enfantId: string | number) => [...VACCINATIONS_KEYS.lists(), { enfantId }] as const,
  detail: (id: string | number) => [...VACCINATIONS_KEYS.all, 'detail', id] as const,
};

/**
 * Hook pour récupérer toutes les vaccinations
 */
export const useAllVaccinations = () => {
  const url = buildVaccinationUrl('vaccinations');
  return useAxiosGet<VaccinationDTO[]>(
    VACCINATIONS_KEYS.lists(),
    url
  );
};

/**
 * Hook pour récupérer les vaccinations d'un enfant spécifique
 */
export const useVaccinationsByEnfant = (enfantId: string | number) => {
  const url = buildVaccinationUrl('by_enfant', { enfantId: String(enfantId) });
  return useAxiosGet<VaccinationDTO[]>(
    VACCINATIONS_KEYS.byEnfant(enfantId),
    url
  );
};

/**
 * Hook pour récupérer l'historique des vaccinations d'un enfant
 */
export const useVaccinationHistory = (enfantId: string | number) => {
  const url = buildVaccinationUrl('history', { enfantId: String(enfantId) });
  return useAxiosGet<VaccinationDTO[]>(
    [...VACCINATIONS_KEYS.byEnfant(enfantId), 'history'],
    url
  );
};

/**
 * Hook pour récupérer une vaccination par son ID
 */
export const useVaccinationById = (id: string | number | null | undefined) => {
  const url = buildVaccinationUrl('vaccinations'); // URL de base
  return useAxiosGetById<VaccinationDTO>(
    VACCINATIONS_KEYS.detail(id || ''),
    url,
    id
  );
};

/**
 * Hook pour enregistrer une nouvelle vaccination
 */
export const useCreateVaccination = () => {
  const queryClient = useQueryClient();
  const url = buildVaccinationUrl('create');
  
  return useAxiosPost<VaccinationDTO, VaccinationCreateDTO>(
    url,
    undefined,
    {
      onSuccess: (data: VaccinationDTO) => {
        // Invalider les listes pour rafraîchir les données
        queryClient.invalidateQueries({
          queryKey: VACCINATIONS_KEYS.lists()
        });
        
        // Si la vaccination inclut l'ID de l'enfant, invalider également les requêtes spécifiques à cet enfant
        if (data.enfantId) {
          queryClient.invalidateQueries({
            queryKey: VACCINATIONS_KEYS.byEnfant(data.enfantId)
          });
        }
        console.log('Vaccination enregistrée avec succès');
      }
    }
  );
};

/**
 * Hook pour mettre à jour une vaccination
 */
export const useUpdateVaccination = () => {
  const queryClient = useQueryClient();
  
  return useAxiosPut<VaccinationDTO, VaccinationUpdateDTO & { id: string | number }>(
    buildVaccinationUrl('update'),
    undefined,
    {
      onSuccess: (data: VaccinationDTO, variables: any) => {
        // Invalider toutes les listes
        queryClient.invalidateQueries({
          queryKey: VACCINATIONS_KEYS.lists()
        });
        
        // Invalider la requête détaillée pour cette vaccination
        queryClient.invalidateQueries({
          queryKey: VACCINATIONS_KEYS.detail(variables.id)
        });
        
        // Si la vaccination inclut l'ID de l'enfant, invalider également les requêtes spécifiques à cet enfant
        if (data.enfantId) {
          queryClient.invalidateQueries({
            queryKey: VACCINATIONS_KEYS.byEnfant(data.enfantId)
          });
        }
        
        console.log(`Vaccination ${variables.id} mise à jour avec succès`);
      }
    }
  );
};

/**
 * Hook pour supprimer une vaccination
 */
export const useDeleteVaccination = () => {
  const queryClient = useQueryClient();
  const baseUrl = buildVaccinationUrl('vaccinations');
  
  return useAxiosDelete(
    baseUrl,
    undefined,
    {
      onSuccess: (_data: any, variables: string | number) => {
        // Invalider toutes les listes après suppression
        queryClient.invalidateQueries({
          queryKey: VACCINATIONS_KEYS.lists()
        });
        
        console.log(`Vaccination ${variables} supprimée avec succès`);
      }
    }
  );
};
