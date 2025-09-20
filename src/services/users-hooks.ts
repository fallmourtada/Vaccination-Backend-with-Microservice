import type { EnfantDTO, EnfantUpdateDTO, UserCreateDTO, UserDTO, UserUpdateDTO } from "@/types";
import { buildUserUrl } from "@/utils/api-config";
import { useAxiosDelete, useAxiosGet, useAxiosPost, useAxiosPut } from "@/hooks/use-api";
import { useQueryClient } from "@tanstack/react-query";


/** 
 * Clés de requête pour les utilisateurs
 */
const USERS_KEYS = {
  all: ['users'] as const,
  lists: () => [...USERS_KEYS.all, 'list'] as const,
  detail: (id: string | number) => [...USERS_KEYS.all, 'detail', id] as const,
};


/** 
 * Hook pour récupérer tous les utilisateurs
 */
export const useAllUsers = () => {
  const url = buildUserUrl('users');
  return useAxiosGet<UserDTO[]>(
    USERS_KEYS.lists(),
    url
  );
};

/** 
 * Hook pour récupérer un utilisateur par son ID
 */
export const useUserById = (userId: string | number) => {
  const url = buildUserUrl('users', { userId: String(userId) });
  return useAxiosGet<UserDTO>(
    USERS_KEYS.detail(userId),
    url
  );
};

/** 
 * Hook pour récupérer le profil de l'utilisateur actuel
 */
export const useUserProfile = (userId: string | number) => {
  const url = buildUserUrl('profile', { userId });
  return useAxiosGet<UserDTO>(
    [...USERS_KEYS.all, 'profile', userId],
    url
  );
};

/** 
 * Hook pour récupérer tous les enfants
 */
export const useAllEnfants = () => {
  const url = buildUserUrl('enfants');
  return useAxiosGet<UserDTO[]>(
    [...USERS_KEYS.all, 'enfants'],
    url
  );
};

/**
 * Hook pour créer un utilisateur
 */
export const useCreateUser = () => {
  const queryClient = useQueryClient();
  const url = buildUserUrl('users');

  return useAxiosPost<UserDTO, UserCreateDTO>(
    url,
    undefined,
    {
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: USERS_KEYS.lists() });
      }
    }
  );
};

/**
 * Hook pour créer un enfant
 */
export const useCreateEnfant = () => {
  const queryClient = useQueryClient();
  const url = buildUserUrl('enfants');

  return useAxiosPost<UserDTO, UserCreateDTO>(
    url,
    undefined,
    {
      onSuccess: (data: EnfantDTO) => {
        queryClient.invalidateQueries({ queryKey: USERS_KEYS.lists() });

        if(data.parentId) {
          queryClient.invalidateQueries({ queryKey: USERS_KEYS.detail(data.parentId) });
        }
      }
    }
  );
};

/**
 * Hook pour mettre à jour un utilisateur
 */
export const useUpdateUser = () => {
  const queryClient = useQueryClient();
  const url = buildUserUrl('update');

  return useAxiosPost<UserDTO, UserUpdateDTO & { id: string | number }>(
    url,
    undefined,
    {
      onSuccess: (_data: UserDTO) => {
        queryClient.invalidateQueries({ queryKey: USERS_KEYS.lists() });
      }
    }
  );
};

/**
 * Hook pour mettre à jour un enfant
 */
export const useUpdateEnfant = () => {
  const queryClient = useQueryClient();
  const url = buildUserUrl('update');

  return useAxiosPut<EnfantDTO, EnfantUpdateDTO & { id: string | number } >(
    url,
    undefined,
    {
      onSuccess: (data: EnfantDTO, variables: { id: string | number }) => {
        queryClient.invalidateQueries({ queryKey: USERS_KEYS.lists() });

        if(data.parentId) {
          queryClient.invalidateQueries({ queryKey: USERS_KEYS.detail(data.parentId) });
        }

        console.log(`Enfant ${variables.id} mis à jour avec succès`);
      }
    }
  );
};

/**
 * Hook pour supprimer un utilisateur
 */
export const useDeleteUser = () => {
  const queryClient = useQueryClient();
  const url = buildUserUrl('users');

  return useAxiosDelete(
    url,
    undefined,
    {
      onSuccess: (_data: any, variables: string | number) => {
        queryClient.invalidateQueries({ queryKey: USERS_KEYS.lists() });

        console.log(`Utilisateur ${variables} supprimé avec succès`);
      }
    }
  );
};

/**
 * Hook pour supprimer un enfant
 */
export const useDeleteEnfant = () => {
  const queryClient = useQueryClient();
  const url = buildUserUrl('enfants');

  return useAxiosDelete(
    url,
    undefined,
    {
      onSuccess: (_data: any, variables: string | number) => {
        queryClient.invalidateQueries({ queryKey: USERS_KEYS.lists() });

        console.log(`Enfant ${variables} supprimé avec succès`);
      }
    }
  );
};


