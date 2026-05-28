package com.jardvcode.erp.inventory.exception.inventory;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class ItemDoesNotExistException extends DomainError {
    private final static String message = "El articulo no existe.";

    public ItemDoesNotExistException(DomainErrorType errorType) {
        super(errorType, message);
    }

    public ItemDoesNotExistException() {
        super(message);
    }
}