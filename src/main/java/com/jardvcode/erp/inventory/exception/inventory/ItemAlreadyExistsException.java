package com.jardvcode.erp.inventory.exception.inventory;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class ItemAlreadyExistsException extends DomainError {
    public ItemAlreadyExistsException(DomainErrorType errorType) {
        super(errorType, "El nombre de articulo ya está en uso.");
    }
}
