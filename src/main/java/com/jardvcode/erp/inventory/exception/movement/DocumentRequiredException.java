package com.jardvcode.erp.inventory.exception.movement;

import com.jardvcode.erp.shared.domain.DomainError;

public final class DocumentRequiredException extends DomainError {
    public DocumentRequiredException() {
        super("La factura es obligatoria.");
    }
}