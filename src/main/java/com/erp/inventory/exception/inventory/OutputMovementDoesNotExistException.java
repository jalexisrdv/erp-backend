package com.erp.inventory.exception.inventory;

import com.erp.shared.domain.DomainError;

public final class OutputMovementDoesNotExistException extends DomainError {
    public OutputMovementDoesNotExistException(Integer httpCode) {
        super(httpCode, "El movimiento de salida no existe.");
    }
}