import type { AppointmentDTO, AppointmentCreateDTO, AppointmentUpdateDTO } from "@/types";
import apiClient from "@/utils/api-client";
import { buildAppointmentUrl } from "@/utils/api-config";


// ================================
// SERVICE RENDEZ-VOUS
// ================================
export class AppointmentService {
  static async getAppointments(): Promise<AppointmentDTO[]> {
    const url = buildAppointmentUrl('appointments');
    const response = await apiClient.get<AppointmentDTO[]>(url);
    return response.data.data;
  }

  static async getAppointmentsByUser(userId: string): Promise<AppointmentDTO[]> {
    const url = buildAppointmentUrl('by_user', { userId });
    const response = await apiClient.get<AppointmentDTO[]>(url);
    return response.data.data;
  }

  static async getAppointmentsByEnfant(enfantId: string): Promise<AppointmentDTO[]> {
    const url = buildAppointmentUrl('by_enfant', { enfantId });
    const response = await apiClient.get<AppointmentDTO[]>(url);
    return response.data.data;
  }

  static async createAppointment(appointmentData: AppointmentCreateDTO): Promise<AppointmentDTO> {
    const url = buildAppointmentUrl('create');
    const response = await apiClient.post<AppointmentDTO>(url, appointmentData);
    return response.data.data;
  }

  static async updateAppointment(appointmentId: string, appointmentData: AppointmentUpdateDTO): Promise<AppointmentDTO> {
    const url = buildAppointmentUrl('update', { appointmentId });
    const response = await apiClient.put<AppointmentDTO>(url, appointmentData);
    return response.data.data;
  }

  static async cancelAppointment(appointmentId: string): Promise<void> {
    const url = buildAppointmentUrl('cancel', { appointmentId });
    await apiClient.delete(url);
  }
}