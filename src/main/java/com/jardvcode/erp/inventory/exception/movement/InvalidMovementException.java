package com.jardvcode.erp.inventory.exception.movement;

import com.jardvcode.erp.shared.domain.DomainError;

public final class InvalidMovementException extends DomainError {
    public InvalidMovementException() {
        super("El valor de movimiento no es válido.");
    }
}