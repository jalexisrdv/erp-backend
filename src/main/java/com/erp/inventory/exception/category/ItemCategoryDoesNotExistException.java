package com.erp.inventory.exception.category;

import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.DomainErrorType;

public final class ItemCategoryDoesNotExistException extends DomainError {
    public ItemCategoryDoesNotExistException(DomainErrorType errorType) {
        super(errorType, "La categoria no existe.");
    }
}