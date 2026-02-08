package com.erp.report.exception.template.item;

import com.erp.shared.domain.DomainError;

public final class ItemDoesNotExistException extends DomainError {
    public ItemDoesNotExistException() {
        super("El item no existe.");
    }
}
