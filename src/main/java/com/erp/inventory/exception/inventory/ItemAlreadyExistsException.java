package com.erp.inventory.exception.inventory;

import com.erp.shared.domain.DomainError;

public final class ItemAlreadyExistsException extends DomainError {
    public ItemAlreadyExistsException(Integer httpCode) {
        super(httpCode, "El nombre de articulo ya está en uso.");
    }
}
