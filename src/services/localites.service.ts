import apiClient from "@/utils/api-client";
import { buildLocaliteUrl } from "@/utils/api-config";


// ================================
// SERVICE LOCALITÉ
// ================================
export class LocaliteService {
  static async getLocalites() {
    const url = buildLocaliteUrl('localites');
    const response = await apiClient.get(url);
    return response.data.data;
  }

  static async getRegions() {
    const url = buildLocaliteUrl('regions');
    const response = await apiClient.get(url);
    return response.data.data;
  }

  static async getDepartments(regionId: string) {
    const url = buildLocaliteUrl('departments', { regionId });
    const response = await apiClient.get(url);
    return response.data.data;
  }

  static async getCommunes(departmentId: string) {
    const url = buildLocaliteUrl('communes', { departmentId });
    const response = await apiClient.get(url);
    return response.data.data;
  }
}