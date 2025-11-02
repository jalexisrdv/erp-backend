package com.erp.inventory.exception.inventory;

import com.erp.shared.domain.DomainError;

public final class EntryMovementDoesNotExistException extends DomainError {
    public EntryMovementDoesNotExistException(Integer httpCode) {
        super(httpCode, "El movimiento de entrada no existe.");
    }
}