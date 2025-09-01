# Architecture Microservices - Gestion Vaccination Sénégal

## 📋 Résumé de l'implémentation

### ✅ Microservices Créés

#### 1. **user-service** (Port: 8081)
- **Responsabilité** : Gestion des utilisateurs et enfants
- **Entités** : Utilisateur, Enfant
- **Base de données** : PostgreSQL (`gestion_vaccination_users`)
- **Localisation** : Utilise les IDs (regionId, departementId, communeId) et enrichit via location-service

#### 2. **location-service** (Port: 8082)
- **Responsabilité** : Gestion des localités géographiques du Sénégal
- **Entité** : Locality (hiérarchique auto-référentielle)
- **Base de données** : PostgreSQL (`gestion_vaccination_location`)
- **Hiérarchie** : REGION → DEPARTMENT → DISTRICT/COMMUNE → NEIGHBORHOOD

### 🏗️ Architecture des Clients

```
user-service/
├── client/
│   ├── rest/           # Appels REST vers microservices externes
│   │   └── LocationServiceClient.java
│   ├── dto/            # DTOs pour microservices externes
│   │   └── LocationDTO.java
│   └── enumeration/    # Énumérations des microservices externes
│       └── LocalityType.java
```

### 🔄 Service d'Enrichissement

**EntityEnrichmentService** dans user-service :
- **Principe** : Existe UNIQUEMENT si le microservice fait appel à d'autres microservices
- **Fonction** : Enrichir les entités avec des données provenant des microservices externes
- **Exemple** : Enrichit les IDs de localisation (regionId, departementId, communeId) avec les données complètes du location-service

### 📊 Modèle de Données

#### Location Service - Locality
```java
{
  "id": 1,
  "name": "Dakar",
  "codification": "DK",
  "type": "REGION",
  "parent": null,
  "population": 3137196,
  "superficieKm2": 550.0,
  "chefLieu": "Dakar",
  "latitude": 14.6928,
  "longitude": -17.4467,
  "active": true
}
```

#### User Service - Enrichissement
```java
// Dans l'entité Enfant
@Column(name = "region_id")
private Long regionId; // Stocké en base

@Transient
private Object region; // Enrichi par EntityEnrichmentService
```

### 🔗 Communication Inter-Services

```java
// Dans user-service
@FeignClient(name = "location-service", path = "/location-service/api/v1/localities")
public interface LocationServiceClient {
    @GetMapping("/{id}")
    LocationDTO obtenirLocaliteParId(@PathVariable Long id);
}

// Dans EntityEnrichmentService
if (enfant.getRegionId() != null) {
    LocationDTO region = locationServiceClient.obtenirLocaliteParId(enfant.getRegionId());
    enfant.setRegion(region);
}
```

### 🎯 Endpoints Principaux

#### Location Service
```http
GET    /location-service/api/v1/localities/{id}
GET    /location-service/api/v1/localities/types/regions
GET    /location-service/api/v1/localities/types/regions/{regionId}/departments
GET    /location-service/api/v1/localities/types/departments/{departmentId}/communes
```

#### User Service
```http
GET    /user-service/api/utilisateurs/{id}      # Avec enrichissement localisation
GET    /user-service/api/enfants/{id}           # Avec enrichissement localisation
```

### ⚙️ Configuration Services

#### Eureka Discovery
- **Eureka Server** : Port 8761
- **user-service** : Enregistré comme "user-service"
- **location-service** : Enregistré comme "location-service"

#### Base de Données
- **user-service** : `gestion_vaccination_users`
- **location-service** : `gestion_vaccination_location`

### 🚀 Prochaines Étapes

1. **vaccine-service** - Gestion des vaccins et calendriers vaccinaux
2. **vaccination-service** - Gestion des actes de vaccination
3. **appointment-service** - Gestion des rendez-vous
4. **reminder-service** - Gestion des rappels et notifications
5. **vaccination-center-service** - Gestion des centres de vaccination
6. **reporting-service** - Statistiques et rapports
7. **notification-service** - Envoi de SMS/Email
8. **gateway-service** - Point d'entrée unique

### 📝 Règles d'Architecture

1. **EntityEnrichmentService** : Créé UNIQUEMENT si le microservice consomme d'autres microservices
2. **Structure client** : 
   - `client/rest/` pour les appels REST
   - `client/dto/` pour les DTOs externes
   - `client/enumeration/` pour les énumérations externes
3. **Localisation** : Stockage par IDs, enrichissement par appels REST
4. **DTOs** : Pattern SaveDTO/UpdateDTO/DTO respecté partout
5. **Documentation** : Swagger sur tous les endpoints

---

**Architecture validée et opérationnelle** ✅  
**Prêt pour l'implémentation des microservices suivants** 🚀
