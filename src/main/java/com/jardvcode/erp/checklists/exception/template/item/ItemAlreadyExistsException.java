package com.jardvcode.erp.checklists.exception.template.item;

import com.jardvcode.erp.shared.domain.DomainError;

public final class ItemAlreadyExistsException extends DomainError {
    public ItemAlreadyExistsException() {
        super("El nombre de item ya está en uso.");
    }
}
