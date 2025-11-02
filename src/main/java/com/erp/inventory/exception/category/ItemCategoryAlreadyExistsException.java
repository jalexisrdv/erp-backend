package com.erp.inventory.exception.category;

import com.erp.shared.domain.DomainError;

public final class ItemCategoryAlreadyExistsException extends DomainError {
    public ItemCategoryAlreadyExistsException(Integer httpCode) {
        super(httpCode, "El nombre de categoria ya está en uso.");
    }
}
