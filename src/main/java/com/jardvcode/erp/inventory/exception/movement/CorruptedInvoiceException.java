package com.jardvcode.erp.inventory.exception.movement;

import com.jardvcode.erp.shared.domain.DomainError;

public final class CorruptedInvoiceException extends DomainError {
    public CorruptedInvoiceException() {
        super("La factura adjunta no se puede leer o está corrupta");
    }
}
