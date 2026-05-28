package com.jardvcode.erp.inventory.exception.movement;

import com.jardvcode.erp.shared.domain.DomainError;

public final class InvoiceRequiredException extends DomainError {
    public InvoiceRequiredException() {
        super("La factura es obligatoria.");
    }
}