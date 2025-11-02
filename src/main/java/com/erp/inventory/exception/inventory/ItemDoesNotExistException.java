package com.erp.inventory.exception.inventory;

import com.erp.shared.domain.DomainError;

public final class ItemDoesNotExistException extends DomainError {
    public ItemDoesNotExistException(Integer httpCode) {
        super(httpCode, "El articulo no existe.");
    }
}