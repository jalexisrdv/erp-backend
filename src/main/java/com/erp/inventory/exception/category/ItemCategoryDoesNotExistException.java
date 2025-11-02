package com.erp.inventory.exception.category;

import com.erp.shared.domain.DomainError;

public final class ItemCategoryDoesNotExistException extends DomainError {
    public ItemCategoryDoesNotExistException(Integer httpCode) {
        super(httpCode, "La categoria no existe.");
    }
}