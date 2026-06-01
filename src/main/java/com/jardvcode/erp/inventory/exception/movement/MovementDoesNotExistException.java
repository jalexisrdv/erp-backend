package com.jardvcode.erp.inventory.exception.movement;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class MovementDoesNotExistException extends DomainError {
    private static final String message = "El movimiento no existe.";

    public MovementDoesNotExistException(DomainErrorType domainErrorType) {
        super(domainErrorType, message);
    }

    public MovementDoesNotExistException() {
        super(message);
    }
}