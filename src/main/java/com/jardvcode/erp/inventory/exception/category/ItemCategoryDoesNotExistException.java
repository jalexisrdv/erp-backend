package com.jardvcode.erp.inventory.exception.category;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class ItemCategoryDoesNotExistException extends DomainError {
    public ItemCategoryDoesNotExistException(DomainErrorType errorType) {
        super(errorType, "La categoria no existe.");
    }
}