import type { ServiceConfig, UrlParams } from '@/types';

// ================================
// CONFIGURATION DES MICROSERVICES
// ================================

export const GATEWAY_URL = import.meta.env.VITE_API_URL || 'http://localhost:9999';

// Configuration typée avec 'as const' pour l'inférence stricte des types littéraux
export const MICROSERVICES_CONFIG = {
  USER_SERVICE: {
    baseURL: GATEWAY_URL,
    basePath: '/api/v1/users',
    endpoints: {
      users: '',
      enfants: '/enfants',
      profile: '/profile/{userId}',
      create: '/create',
      update: '/update/{userId}'
    }
  },
  LOCALITE_SERVICE: {
    baseURL: GATEWAY_URL,
    basePath: '/api/v1/localites',
    endpoints: {
      localites: '',
      regions: '/types/regions',
      departments: '/types/regions/{regionId}/departments',
      communes: '/types/departments/{departmentId}/communes'
    }
  },
  APPOINTMENT_SERVICE: {
    baseURL: GATEWAY_URL,
    basePath: '/api/v1/appointments',
    endpoints: {
      appointments: '',
      by_user: '/user/{userId}',
      by_enfant: '/enfant/{enfantId}',
      create: '/create',
      update: '/update/{appointmentId}',
      cancel: '/cancel/{appointmentId}'
    }
  },
  VACCINATION_SERVICE: {
    baseURL: GATEWAY_URL,
    basePath: '/api/v1/vaccinations',
    endpoints: {
      vaccinations: '',
      by_enfant: '/enfant/{enfantId}',
      history: '/history/{enfantId}',
      create: '/create',
      update: '/update/{vaccinationId}'
    }
  },
  VACCIN_SERVICE: {
    baseURL: GATEWAY_URL,
    basePath: '/api/v1/vaccins',
    endpoints: {
      vaccins: '',
      calendrier: '/calendrier',
      by_age: '/by-age/{ageInMonths}',
      details: '/details/{vaccineId}'
    }
  }
} as const satisfies Record<string, ServiceConfig>;

// Types inférés automatiquement depuis la configuration
type ServiceKey = keyof typeof MICROSERVICES_CONFIG;
type UserEndpoints = keyof typeof MICROSERVICES_CONFIG.USER_SERVICE.endpoints;
type LocationEndpoints = keyof typeof MICROSERVICES_CONFIG.LOCALITE_SERVICE.endpoints;
type AppointmentEndpoints = keyof typeof MICROSERVICES_CONFIG.APPOINTMENT_SERVICE.endpoints;
type VaccinationEndpoints = keyof typeof MICROSERVICES_CONFIG.VACCINATION_SERVICE.endpoints;
type VaccinEndpoints = keyof typeof MICROSERVICES_CONFIG.VACCIN_SERVICE.endpoints;

// ================================
// GÉNÉRATEUR D'URL TYPÉ
// ================================

/**
 * Construit une URL d'API complète avec gestion des paramètres
 * @param service - Le service cible
 * @param endpoint - Le point de terminaison
 * @param params - Les paramètres pour l'URL
 * @returns L'URL complète
 */
export const buildApiUrl = <T extends ServiceKey>(
  service: T,
  endpoint: string,
  params: UrlParams = {}
): string => {
  const config = MICROSERVICES_CONFIG[service];
  if (!config) {
    throw new Error(`Service ${String(service)} non configuré`);
  }
  
  let url = `${config.baseURL}${config.basePath}${endpoint}`;
  
  // Remplacer les paramètres dans l'URL (ex: {regionId})
  Object.entries(params).forEach(([key, value]) => {
    url = url.replace(`{${key}}`, String(value));
  });
  
  return url;
};

// ================================
// FONCTIONS HELPER TYPÉES
// ================================

export const buildUserUrl = (endpoint: UserEndpoints, params: UrlParams = {}) => 
  buildApiUrl('USER_SERVICE', MICROSERVICES_CONFIG.USER_SERVICE.endpoints[endpoint], params);

export const buildLocaliteUrl = (endpoint: LocationEndpoints, params: UrlParams = {}) => 
  buildApiUrl('LOCALITE_SERVICE', MICROSERVICES_CONFIG.LOCALITE_SERVICE.endpoints[endpoint], params);

export const buildAppointmentUrl = (endpoint: AppointmentEndpoints, params: UrlParams = {}) => 
  buildApiUrl('APPOINTMENT_SERVICE', MICROSERVICES_CONFIG.APPOINTMENT_SERVICE.endpoints[endpoint], params);

export const buildVaccinationUrl = (endpoint: VaccinationEndpoints, params: UrlParams = {}) => 
  buildApiUrl('VACCINATION_SERVICE', MICROSERVICES_CONFIG.VACCINATION_SERVICE.endpoints[endpoint], params);

export const buildVaccinUrl = (endpoint: VaccinEndpoints, params: UrlParams = {}) => 
  buildApiUrl('VACCIN_SERVICE', MICROSERVICES_CONFIG.VACCIN_SERVICE.endpoints[endpoint], params);
