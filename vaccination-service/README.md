# Microservice de Gestion des Vaccinations

Responsable de la gestion des événements de vaccination des enfants.

## Pattern "ID + @Transient DTO"
Comme les autres services, on stocke uniquement `localityId` et on enrichit les données via `LocationDTO`.

## Entité Vaccination
| Champ             | Type       | Description                       |
|-------------------|------------|-----------------------------------|
| `id`              | Long       | Identifiant unique                |
| `enfantId`        | Long       | ID de l'enfant vacciné            |
| `vaccineId`       | Long       | ID du vaccin administré           |
| `dateVaccination` | DateTime   | Date et heure de la vaccination   |
| `localityId`      | Long       | ID de la localité                 |

## Endpoints
- POST `/api/vaccinations` : créer
- GET `/api/vaccinations/{id}` : lire
- PUT `/api/vaccinations/{id}` : mettre à jour
- DELETE `/api/vaccinations/{id}` : supprimer
- GET `/api/vaccinations/enfant/{enfantId}`
- GET `/api/vaccinations/vaccine/{vaccineId}`
- GET `/api/vaccinations/localite/{localityId}`
- GET `/api/vaccinations/date?debut=&fin=`
