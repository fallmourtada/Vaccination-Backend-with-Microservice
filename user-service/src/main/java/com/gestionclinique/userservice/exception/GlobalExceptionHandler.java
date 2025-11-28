package com.gestionclinique.userservice.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire global des exceptions pour l'application.
 * Cette classe centralise la gestion des exceptions pour tous les contrôleurs.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gère les exceptions ResourceNotFoundException.
     *
     * @param e       L'exception interceptée
     * @param request La requête Web
     * @return Une réponse HTTP avec le message d'erreur et le code d'état 404 Not Found
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleResourceNotFoundException(
            ResourceNotFoundException e, WebRequest request) {
        ApiError error = new ApiError(
                "Ressource non trouvée",
                e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }



    /**
     * Gère les exceptions de validation de données (Bean Validation)
     *
     * @param e       L'exception interceptée
     * @param request La requête Web
     * @return Une réponse HTTP avec les détails des erreurs de validation et le code d'état 400 Bad Request
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException e, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Erreur de validation des données");
        response.put("errors", validationErrors);
        response.put("path", request.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



    /**
     * Gère les exceptions de violation de contraintes d'intégrité des données
     *
     * @param e       L'exception interceptée
     * @param request La requête Web
     * @return Une réponse HTTP avec le message d'erreur et le code d'état 409 Conflict
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(
            DataIntegrityViolationException e, WebRequest request) {
        ApiError error = new ApiError(
                "Violation de contrainte d'intégrité des données",
                e.getMostSpecificCause().getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }






    /**
     * Gère toutes les autres exceptions non capturées
     *
     * @param e       L'exception interceptée
     * @param request La requête Web
     * @return Une réponse HTTP avec le message d'erreur et le code d'état 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleAllUncaughtException(
            Exception e, WebRequest request) {
        ApiError error = new ApiError(
                "Erreur interne du serveur",
                e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    /**
     * Structure standardisée pour les réponses d'erreur
     */
    private static class ApiError {
        private final LocalDateTime timestamp;
        private final String message;
        private final String details;
        private final String path;

        public ApiError(String message, String details, String path) {
            this.timestamp = LocalDateTime.now();
            this.message = message;
            this.details = details;
            this.path = path;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public String getMessage() {
            return message;
        }

        public String getDetails() {
            return details;
        }

        public String getPath() {
            return path;
        }
    }


}
