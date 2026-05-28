package com.jardvcode.erp.inventory.exception.movement;

import com.jardvcode.erp.shared.domain.DomainError;

public final class MovementDoesNotExistException extends DomainError {
    public MovementDoesNotExistException() {
        super("El movimiento no existe.");
    }
}