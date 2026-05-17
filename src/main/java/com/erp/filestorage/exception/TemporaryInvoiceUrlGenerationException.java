package com.erp.filestorage.exception;

import com.erp.shared.domain.DomainError;

public final class TemporaryInvoiceUrlGenerationException extends DomainError {
    public TemporaryInvoiceUrlGenerationException() {
        super("No se pudo generar la URL temporal para la factura");
    }
}
