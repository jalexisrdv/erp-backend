package com.erp.inventory.exception.movement;

import com.erp.shared.domain.DomainError;

public final class MovementDoesNotExistException extends DomainError {
    public MovementDoesNotExistException() {
        super("El movimiento no existe.");
    }
}