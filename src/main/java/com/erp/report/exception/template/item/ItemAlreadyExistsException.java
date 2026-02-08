package com.erp.report.exception.template.item;

import com.erp.shared.domain.DomainError;

public final class ItemAlreadyExistsException extends DomainError {
    public ItemAlreadyExistsException() {
        super("El nombre de item ya está en uso.");
    }
}
