# User Service - Gestion de Vaccination Sénégal

## 📋 Description

Le **User Service** est un microservice Spring Boot responsable de la gestion des utilisateurs et des enfants dans le système de vaccination du Sénégal. Il fait partie d'une architecture microservices complète pour la gestion de la vaccination.

## 🏗️ Architecture

### Entités principales
- **Utilisateur** : Gestion des utilisateurs (parents, médecins, administrateurs, agents de santé)
- **Enfant** : Gestion des enfants et de leurs informations médicales

### Rôles utilisateur
- `PARENT` : Parents d'enfants
- `MEDECIN` : Médecins
- `ADMINISTRATEUR` : Administrateurs système
- `AGENT_SANTE` : Agents de santé
- `GESTIONNAIRE_CENTRE` : Gestionnaires de centres de vaccination
- `SAGE_FEMME` : Sages-femmes
- `INFIRMIER` : Infirmiers/Infirmières

### Régions du Sénégal supportées
- Dakar, Thiès, Saint-Louis, Diourbel, Louga, Tambacounda
- Kaolack, Kolda, Ziguinchor, Fatick, Kaffrine, Kédougou, Matam, Sédhiou

## 🚀 Technologies utilisées

- **Spring Boot 3.2.1**
- **Spring Data JPA**
- **PostgreSQL**
- **Spring Cloud (Eureka, OpenFeign, Config)**
- **Keycloak** (Authentification/Autorisation)
- **Swagger/OpenAPI 3** (Documentation)
- **Lombok** (Réduction du code boilerplate)
- **Maven** (Gestion des dépendances)

## 📁 Structure du projet

```
src/
├── main/
│   ├── java/com/gestionvaccination/userservice/
│   │   ├── controller/          # Contrôleurs REST
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── entites/             # Entités JPA
│   │   ├── enumeration/         # Énumérations
│   │   ├── exception/           # Exceptions personnalisées
│   │   ├── mapper/              # Mappers (Entity ↔ DTO)
│   │   ├── repository/          # Repositories JPA
│   │   ├── services/            # Interfaces des services
│   │   ├── servicesImpl/        # Implémentations des services
│   │   ├── utils/               # Classes utilitaires
│   │   └── UserServiceApplication.java
│   └── resources/
│       ├── application.yml      # Configuration
│       └── data.sql            # Données de test
```

## 🔧 Configuration

### Base de données
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/gestion_vaccination_users
    username: postgres
    password: password
```

### Eureka
```yaml
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

### Keycloak
```yaml
keycloak:
  auth-server-url: http://localhost:8080/auth
  realm: gestion-vaccination
  resource: user-service
```

## 🌐 APIs disponibles

### Utilisateurs
- `POST /api/utilisateurs` - Créer un utilisateur
- `GET /api/utilisateurs/{id}` - Obtenir un utilisateur
- `PUT /api/utilisateurs/{id}` - Mettre à jour un utilisateur
- `DELETE /api/utilisateurs/{id}` - Supprimer un utilisateur
- `GET /api/utilisateurs/role/{role}` - Utilisateurs par rôle
- `GET /api/utilisateurs/region/{region}` - Utilisateurs par région

### Enfants
- `POST /api/enfants` - Créer un enfant
- `GET /api/enfants/{id}` - Obtenir un enfant
- `GET /api/enfants/qr/{codeQr}` - Obtenir un enfant par QR
- `PUT /api/enfants/{id}` - Mettre à jour un enfant
- `GET /api/enfants/parent/{parentId}` - Enfants d'un parent
- `GET /api/enfants/age?ageMinMois=X&ageMaxMois=Y` - Enfants par âge

## 🔍 Documentation API

La documentation Swagger est disponible à : `http://localhost:8081/swagger-ui.html`

## 🏃‍♂️ Démarrage

### Prérequis
- Java 17
- Maven 3.6+
- PostgreSQL 13+
- Keycloak 23+

### Installation
1. Cloner le projet
2. Configurer PostgreSQL et créer la base `gestion_vaccination_users`
3. Configurer Keycloak avec le realm `gestion-vaccination`
4. Démarrer les services d'infrastructure (Eureka, Config Server)
5. Lancer l'application :
```bash
mvn spring-boot:run
```

## 📊 Monitoring

- **Health Check** : `http://localhost:8081/actuator/health`
- **Metrics** : `http://localhost:8081/actuator/metrics`
- **Info** : `http://localhost:8081/actuator/info`

## 🔐 Sécurité

L'authentification et l'autorisation sont gérées par Keycloak. Chaque endpoint nécessite une authentification appropriée selon le rôle de l'utilisateur.

## 📈 Fonctionnalités principales

### Gestion des utilisateurs
- Création et authentification via Keycloak
- Gestion des rôles et permissions
- Recherche et filtrage par critères
- Gestion des statuts (actif, inactif, suspendu)

### Gestion des enfants
- Enregistrement avec génération de QR code unique
- Calcul automatique de l'âge
- Gestion des informations médicales
- Liaison avec les parents
- Recherche et filtrage avancés

### Intégration microservices
- Communication avec les services de vaccination
- Enrichissement des données via Feign clients
- Gestion centralisée de la configuration

## 🧪 Tests

```bash
mvn test
```

## 🤝 Contribution

1. Fork le projet
2. Créer une branche feature
3. Commit les changements
4. Push vers la branche
5. Créer une Pull Request

## 📞 Contact

Équipe Gestion Vaccination - contact@gestionvaccination.sn
