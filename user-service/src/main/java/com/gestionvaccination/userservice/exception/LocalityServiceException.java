package com.gestionvaccination.userservice.exception;

/**
 * Exception levée lorsqu'une erreur se produit lors de la communication avec le service de localité
 */
public class LocalityServiceException extends RuntimeException {

    public LocalityServiceException(String message) {
        super(message);
    }

    public LocalityServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
