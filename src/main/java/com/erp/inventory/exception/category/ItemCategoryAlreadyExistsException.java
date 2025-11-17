package com.erp.inventory.exception.category;

import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.DomainErrorType;

public final class ItemCategoryAlreadyExistsException extends DomainError {
    public ItemCategoryAlreadyExistsException(DomainErrorType errorType) {
        super(errorType, "El nombre de categoria ya está en uso.");
    }
}
