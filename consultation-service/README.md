# Microservice de Gestion des Vaccins

Ce microservice fait partie de l'architecture microservices du système de gestion de la vaccination des enfants au Sénégal. Il est responsable de la gestion des données relatives aux vaccins.

## Architecture

Ce microservice suit le pattern "ID + @Transient DTO" pour l'intégration des données externes :
- Chaque entité stocke uniquement l'ID des références à d'autres entités dans d'autres microservices
- Les objets enrichis sont stockés dans des champs @Transient et peuplés lors de la récupération

## Fonctionnalités

- Gestion complète des vaccins (CRUD)
- Recherche de vaccins par différents critères
- Gestion des stocks de vaccins
- Information sur la compatibilité des vaccins selon l'âge
- Gestion de la localisation des stocks de vaccins

## Endpoints API

### Vaccins

- `GET /api/vaccines` : Liste des vaccins avec pagination
- `GET /api/vaccines/{id}` : Détails d'un vaccin par ID
- `POST /api/vaccines` : Créer un nouveau vaccin
- `PUT /api/vaccines/{id}` : Mettre à jour un vaccin existant
- `DELETE /api/vaccines/{id}` : Supprimer un vaccin
- `GET /api/vaccines/search` : Recherche avancée de vaccins
- `GET /api/vaccines/type/{typeVaccin}` : Vaccins par type
- `GET /api/vaccines/expiration-avant/{date}` : Vaccins expirant avant une date
- `GET /api/vaccines/disponibles` : Vaccins disponibles
- `GET /api/vaccines/pour-age/{ageMois}` : Vaccins adaptés à un âge donné
- `PATCH /api/vaccines/{id}/stock` : Mettre à jour le stock d'un vaccin
- `GET /api/vaccines/localite/{localiteId}` : Vaccins par localité

## Modèle de données

### Entité Vaccine

- `id` : Identifiant unique du vaccin
- `nom` : Nom du vaccin
- `fabricant` : Fabricant du vaccin
- `numeroLot` : Numéro de lot
- `dateProduction` : Date de production
- `dateExpiration` : Date d'expiration
- `description` : Description du vaccin
- `dosage` : Information sur le dosage
- `typeVaccin` : Type de vaccin (énumération)
- `modeAdministration` : Mode d'administration (énumération)
- `temperatureConservation` : Température de conservation
- `contreIndications` : Contre-indications
- `effetsSecondaires` : Effets secondaires possibles
- `ageMinimumMois` : Âge minimum en mois
- `ageMaximumMois` : Âge maximum en mois
- `dosesRequises` : Nombre de doses requises
- `intervalleEntresDosesJours` : Intervalle entre les doses en jours
- `rappelRecommande` : Si un rappel est recommandé
- `intervalleRappelMois` : Intervalle pour le rappel en mois
- `localityId` : ID de la localité où le vaccin est stocké
- `quantiteDisponible` : Quantité disponible en stock

## Intégration avec les autres services

Ce microservice communique avec :
- `location-service` : Pour obtenir les détails des localités où les vaccins sont stockés
