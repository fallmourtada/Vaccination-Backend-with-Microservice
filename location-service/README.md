# Location Service - Microservice de Gestion des Localités

## 📋 Description

Le **Location Service** est un microservice dédié à la gestion des localités géographiques du Sénégal dans le cadre du système de vaccination des enfants. Il utilise une approche hiérarchique flexible pour gérer les régions, départements, arrondissements, communes et quartiers.

## 🏗️ Architecture

### Structure Hiérarchique
```
REGION (14 régions)
├── DEPARTMENT (45 départements)
    ├── DISTRICT (arrondissements - optionnel)
    │   └── COMMUNE (communes)
    │       └── NEIGHBORHOOD (quartiers)
    └── COMMUNE (communes directes)
        └── NEIGHBORHOOD (quartiers)
```

### Entité Principale : Locality
- **ID** : Identifiant unique auto-généré
- **Name** : Nom de la localité
- **Type** : Type de localité (REGION, DEPARTMENT, DISTRICT, COMMUNE, NEIGHBORHOOD)
- **Parent** : Référence vers la localité parente
- **Children** : Collection des localités enfants
- **Informations supplémentaires** : Population, superficie, coordonnées GPS, etc.

## 🚀 Fonctionnalités

### Gestion CRUD
- ✅ Création, lecture, mise à jour et suppression de localités
- ✅ Validation des données d'entrée
- ✅ Gestion des erreurs avec réponses standardisées

### Recherche et Filtrage
- ✅ Recherche par nom (insensible à la casse)
- ✅ Filtrage par type de localité
- ✅ Filtrage par parent (localités enfants)
- ✅ Récupération des localités actives

### Endpoints Spécialisés par Type
- ✅ **Régions** : CRUD et gestion des régions du Sénégal
- ✅ **Départements** : Gestion par région
- ✅ **Arrondissements** : Gestion par département
- ✅ **Communes** : Gestion par département ou arrondissement
- ✅ **Quartiers** : Gestion par commune

### Fonctionnalités Avancées
- ✅ **Descendants de communes** : Récupération de tous les IDs de communes descendantes d'une localité
- ✅ **Enrichissement de données** : Support pour les coordonnées GPS, population, superficie
- ✅ **Activation/Désactivation** : Gestion du statut actif des localités

## 🛠️ Technologies Utilisées

- **Spring Boot 3.2.1** - Framework principal
- **Spring Data JPA** - Persistance des données
- **PostgreSQL** - Base de données
- **Spring Cloud Netflix Eureka** - Découverte de services
- **OpenAPI 3 / Swagger** - Documentation API
- **Jakarta Validation** - Validation des données
- **Lombok** - Réduction du code boilerplate

## 📡 Endpoints API

### Endpoints Principaux
```http
GET    /api/v1/localities/{id}              # Récupérer une localité
POST   /api/v1/localities                   # Créer une localité
PUT    /api/v1/localities/{id}              # Mettre à jour une localité
DELETE /api/v1/localities/{id}              # Supprimer une localité
GET    /api/v1/localities/search?name={}    # Rechercher par nom
GET    /api/v1/localities/active            # Localités actives
```

### Endpoints par Type de Localité
```http
# Régions
GET    /api/v1/localities/types/regions
POST   /api/v1/localities/types/regions

# Départements
GET    /api/v1/localities/types/departments
GET    /api/v1/localities/types/regions/{regionId}/departments
POST   /api/v1/localities/types/regions/{regionId}/departments

# Communes
GET    /api/v1/localities/types/communes
GET    /api/v1/localities/types/departments/{departmentId}/communes
GET    /api/v1/localities/types/regions/{regionId}/communes

# Quartiers
GET    /api/v1/localities/types/communes/{communeId}/neighborhoods
POST   /api/v1/localities/types/communes/{communeId}/neighborhoods
```

### Endpoint Spécialisé
```http
GET /api/v1/localities/children-communes?parentLocalityId={id}
# Récupère tous les IDs de communes descendantes d'une localité
```

## 🔧 Configuration

### Base de Données
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/gestion_vaccination_location
    username: vaccination_user
    password: vaccination_password
```

### Service Discovery
```yaml
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

### Port et Context
```yaml
server:
  port: 8082
  servlet:
    context-path: /location-service
```

## 📊 Modèle de Données

### DTOs
- **LocalityDTO** : Affichage complet des données
- **SaveLocalityDTO** : Création d'une nouvelle localité
- **UpdateLocalityDTO** : Mise à jour d'une localité existante

### Exemple de Structure JSON
```json
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
  "active": true,
  "description": "Région de Dakar, capitale du Sénégal"
}
```

## 🧪 Tests

```bash
# Exécuter les tests
mvn test

# Avec couverture
mvn test jacoco:report
```

## 🚀 Démarrage

### Prérequis
- Java 17+
- PostgreSQL
- Eureka Server (port 8761)

### Lancement
```bash
# Compiler
mvn clean compile

# Lancer l'application
mvn spring-boot:run

# Ou avec le JAR
java -jar target/location-service-1.0.0.jar
```

### Vérification
- **Service** : http://localhost:8082/location-service
- **Swagger UI** : http://localhost:8082/location-service/swagger-ui.html
- **API Docs** : http://localhost:8082/location-service/api-docs
- **Health Check** : http://localhost:8082/location-service/actuator/health

## 🔗 Intégration avec User Service

Le `user-service` utilise ce microservice via un client Feign pour enrichir les données de localisation des utilisateurs et enfants :

```java
@FeignClient(name = "location-service", path = "/location-service/api/v1/localities")
public interface LocationServiceClient {
    @GetMapping("/{id}")
    LocalityDTO getLocalityById(@PathVariable Long id);
    
    @GetMapping("/types/regions/{regionId}/communes")
    List<LocalityDTO> getCommunesByRegion(@PathVariable Long regionId);
}
```

## 📈 Monitoring

- **Actuator Endpoints** : `/actuator/health`, `/actuator/metrics`, `/actuator/info`
- **Eureka Dashboard** : Statut du service dans la découverte de services
- **Logs** : Configuration DEBUG pour le développement

## 🤝 Contribution

Ce microservice suit les principes de l'architecture hexagonale et les bonnes pratiques Spring Boot :
- Séparation claire des couches (Controller, Service, Repository)
- DTOs pour l'exposition des APIs
- Mappers pour les conversions
- Gestion globale des exceptions
- Documentation complète avec Swagger

## 📝 Notes Importantes

1. **Flexibilité** : La structure permet d'adapter facilement la hiérarchie administrative sénégalaise
2. **Performance** : Requêtes optimisées pour les recherches par hiérarchie
3. **Évolutivité** : Support pour l'ajout de nouveaux types de localités
4. **Intégration** : Conçu pour s'intégrer seamlessly avec les autres microservices

---

**Auteur** : Équipe de développement Gestion Vaccination Sénégal  
**Version** : 1.0.0  
**Dernière mise à jour** : Décembre 2024
