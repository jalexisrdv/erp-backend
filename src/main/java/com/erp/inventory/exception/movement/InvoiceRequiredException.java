package com.erp.inventory.exception.movement;

import com.erp.shared.domain.DomainError;

public final class InvoiceRequiredException extends DomainError {
    public InvoiceRequiredException() {
        super("La factura es obligatoria.");
    }
}