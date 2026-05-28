package com.jardvcode.erp.reports.exception.template.item;

import com.jardvcode.erp.shared.domain.DomainError;

public final class ItemDoesNotExistException extends DomainError {
    public ItemDoesNotExistException() {
        super("El item no existe.");
    }
}
