import type { ApiError, ApiResponse } from '@/types';
import axios, { type AxiosResponse, type AxiosRequestConfig } from 'axios';
import { GATEWAY_URL } from './api-config';
import { AuthService } from '@/services/auth.service';


// ================================
// CLASSE API CLIENT 
// ================================

class ApiClient {
  private client = axios.create({
    baseURL: GATEWAY_URL,
    timeout: 10000,
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    }
  });

  constructor() {
    this.setupInterceptors();
  }

  private setupInterceptors(): void {
    // Intercepteur pour les requêtes
    this.client.interceptors.request.use(
      (config) => {
        const token = AuthService.getAuthToken();
        if (token) {
          config.headers.Authorization = `Bearer ${token}`;
        }
        
        console.log(`[API Request] ${config.method?.toUpperCase()} ${config.url}`);
        return config;
      },
      (error) => {
        console.error('[API Request Error]', error);
        return Promise.reject(error);
      }
    );

    // Intercepteur pour les réponses
    this.client.interceptors.response.use(
      (response) => {
        console.log(`[API Response] ${response.status} ${response.config.url}`);
        return response;
      },
      (error) => {
        console.error('[API Response Error]', error.response?.data || error.message);
        
        if (error.response?.status === 401) {
          AuthService.handleUnauthorized();
        } else if (error.response?.status === 503) {
          console.error('Microservice indisponible');
        }
        
        return Promise.reject(this.formatError(error));
      }
    );
  }

  private formatError(error: any): ApiError {
    return {
      message: error.response?.data?.message || error.message || 'Une erreur est survenue',
      code: error.response?.data?.code || 'UNKNOWN_ERROR',
      details: error.response?.data
    };
  }

  // Méthodes HTTP typées
  async get<T = any>(url: string, config?: AxiosRequestConfig): Promise<AxiosResponse<ApiResponse<T>>> {
    return this.client.get<ApiResponse<T>>(url, config);
  }

  async post<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<AxiosResponse<ApiResponse<T>>> {
    return this.client.post<ApiResponse<T>>(url, data, config);
  }

  async put<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<AxiosResponse<ApiResponse<T>>> {
    return this.client.put<ApiResponse<T>>(url, data, config);
  }

  async patch<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<AxiosResponse<ApiResponse<T>>> {
    return this.client.patch<ApiResponse<T>>(url, data, config);
  }

  async delete<T = any>(url: string, config?: AxiosRequestConfig): Promise<AxiosResponse<ApiResponse<T>>> {
    return this.client.delete<ApiResponse<T>>(url, config);
  }
}

// Instance singleton
export const apiClient = new ApiClient();

// Export par défaut de l'instance API
export default apiClient;
