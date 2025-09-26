package com.gestionvaccination.userservice.services;

import com.gestionvaccination.userservice.client.dto.EnfantAvecVaccinationsDTO;
import com.gestionvaccination.userservice.client.dto.VaccinationDTO;
import com.gestionvaccination.userservice.dto.*;
import com.gestionvaccination.userservice.enumeration.Sexe;
import com.google.zxing.WriterException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface du service de gestion des enfants.
 * Définit toutes les opérations métier liées à l'entité Enfant.
 */
public interface EnfantService {

    /**
     * Crée un nouvel enfant et génère son code QR.
     *
     * @param saveEnfantDTO Les données de l'enfant à sauvegarder.
     * @param parentId L'ID de l'utilisateur parent associé à l'enfant.
     * @return L'objet EnfantDTO de l'enfant créé.
     * @throws IOException Si une erreur survient lors de la génération du QR code.
     * @throws WriterException Si une erreur survient lors de l'écriture du QR code.
     */
    EnfantDTO saveEnfant(SaveEnfantDTO saveEnfantDTO, Long parentId) throws IOException, WriterException;

    /**
     * Récupère un enfant par son identifiant unique.
     *
     * @param enfantId L'ID de l'enfant.
     * @return L'objet EnfantDTO correspondant.
     */
    EnfantDTO getEnfantById(Long enfantId);

    /**
     * Récupère un enfant en utilisant la donnée encodée dans son QR code.
     *
     * @param codeQr La chaîne de caractères contenue dans le QR code.
     * @return L'objet EnfantDTO correspondant.
     */
    EnfantDTO getEnfantByCodeQr(String codeQr);

    /**
     * Met à jour les informations d'un enfant existant.
     *
     * @param enfantId L'ID de l'enfant à mettre à jour.
     * @param updateEnfantDTO Les nouvelles données à appliquer à l'enfant.
     * @return L'objet EnfantDTO mis à jour.
     */
    EnfantDTO updateEnfant(Long enfantId, UpdateEnfantDTO updateEnfantDTO);

    /**
     * Supprime un enfant de la base de données.
     *
     * @param enfantId L'ID de l'enfant à supprimer.
     */
    void deleteEnfant(Long enfantId);

    /**
     * Récupère la liste de tous les enfants enregistrés.
     *
     * @return Une liste d'objets EnfantDTO.
     */
    List<EnfantDTO> getAllEnfants();

    /**
     * Récupère le carnet de vaccination de l'enfant identifié par un token d'accès.
     * Nécessite un appel au microservice 'vaccination-service'.
     *
     * @param accessToken Le token utilisé pour identifier l'enfant (via le QR code).
     * @return Une liste des vaccinations (VaccinationDTO) associées à l'enfant.
     */
    List<VaccinationDTO> getEnfantWithVaccinationsByAccessToken(String accessToken);

    /**
     * Calcule et retourne le nombre total d'enfants dans le système.
     *
     * @return Le nombre total d'enfants.
     */
    long getNombreTotalEnfants();

    /**
     * Calcule et retourne le nombre total de filles.
     *
     * @return Le nombre total d'enfants de sexe FEMININ.
     */
    long getNombreFilles();

    /**
     * Calcule et retourne le nombre total de garçons.
     *
     * @return Le nombre total d'enfants de sexe MASCULIN.
     */
    long getNombreGarcons();

}