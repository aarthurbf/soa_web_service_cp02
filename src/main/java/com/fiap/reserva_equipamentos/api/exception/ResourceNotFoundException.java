package com.fiap.reserva_equipamentos.api.exception;

/**
 * Exceção lançada quando um recurso não é encontrado no sistema.
 * Utilizada para retornar HTTP 404 Not Found.
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s não encontrado com id: %d", resourceName, id));
    }
}
