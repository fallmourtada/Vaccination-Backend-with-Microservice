import type { CalendrierVaccinalDTO, VaccinDTO } from "@/types";
import apiClient from "@/utils/api-client";
import { buildVaccinUrl } from "@/utils/api-config";


// ================================
// SERVICE VACCINS
// ================================
export class VaccinService {
  static async getVaccins(): Promise<VaccinDTO[]> {
    const url = buildVaccinUrl('vaccins');
    const response = await apiClient.get<VaccinDTO[]>(url);
    return response.data.data;
  }

  static async getCalendrier(): Promise<CalendrierVaccinalDTO> {
    const url = buildVaccinUrl('calendrier');
    const response = await apiClient.get<CalendrierVaccinalDTO>(url);
    return response.data.data;
  }

  static async getVaccinsByAge(ageInMonths: number): Promise<VaccinDTO[]> {
    const url = buildVaccinUrl('by_age', { ageInMonths });
    const response = await apiClient.get<VaccinDTO[]>(url);
    return response.data.data;
  }

  static async getVaccinDetails(vaccinId: string): Promise<VaccinDTO> {
    const url = buildVaccinUrl('details', { vaccinId });
    const response = await apiClient.get<VaccinDTO>(url);
    return response.data.data;
  }
}