import type { EnfantDTO, UserDTO, UserUpdateDTO } from "@/types";
import apiClient from "@/utils/api-client";
import { buildUserUrl } from "@/utils/api-config";



// ================================
// SERVICE UTILISATEUR
// ================================
export class UserService {
  static async getUsers(): Promise<UserDTO[]> {
    const url = buildUserUrl('users');
    const response = await apiClient.get<UserDTO[]>(url);
    return response.data.data;
  }

  static async getEnfants(): Promise<EnfantDTO[]> {
    const url = buildUserUrl('enfants');
    const response = await apiClient.get<EnfantDTO[]>(url);
    return response.data.data;
  }

  static async getUserProfile(userId: string): Promise<UserDTO> {
    const url = buildUserUrl('profile', { userId });
    const response = await apiClient.get<UserDTO>(url);
    return response.data.data;
  }

  static async updateUser(userId: string, userData: UserUpdateDTO): Promise<UserDTO> {
    const url = buildUserUrl('update', { userId });
    const response = await apiClient.put<UserDTO>(url, userData);
    return response.data.data;
  }
}