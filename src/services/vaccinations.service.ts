import type { VaccinationCreateDTO, VaccinationDTO, VaccinationUpdateDTO } from "@/types";
import apiClient from "@/utils/api-client";
import { buildVaccinationUrl } from "@/utils/api-config";

export class VaccinationService {
  static async getVaccinations(): Promise<VaccinationDTO[]> {
    const url = buildVaccinationUrl('vaccinations');
    const response = await apiClient.get<VaccinationDTO[]>(url);
    return response.data.data;
  }

  static async getVaccinationsByEnfant(enfantId: string): Promise<VaccinationDTO[]> {
    const url = buildVaccinationUrl('by_enfant', { enfantId });
    const response = await apiClient.get<VaccinationDTO[]>(url);
    return response.data.data;
  }

  static async getVaccinationHistory(enfantId: string): Promise<VaccinationDTO[]> {
    const url = buildVaccinationUrl('history', { enfantId });
    const response = await apiClient.get<VaccinationDTO[]>(url);
    return response.data.data;
  }

  static async createVaccination(vaccinationData: VaccinationCreateDTO ): Promise<VaccinationDTO> {
    const url = buildVaccinationUrl('create');
    const response = await apiClient.post<VaccinationDTO>(url, vaccinationData);
    return response.data.data;
  }

  static async updateVaccination(vaccinationId: string, vaccinationData: VaccinationUpdateDTO ): Promise<VaccinationDTO> {
    const url = buildVaccinationUrl('update', { vaccinationId });
    const response = await apiClient.put<VaccinationDTO>(url, vaccinationData);
    return response.data.data;
  }
}