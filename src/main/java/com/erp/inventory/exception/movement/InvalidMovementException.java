package com.erp.inventory.exception.movement;

import com.erp.shared.domain.DomainError;

public final class InvalidMovementException extends DomainError {
    public InvalidMovementException() {
        super("El valor de movimiento no es válido.");
    }
}