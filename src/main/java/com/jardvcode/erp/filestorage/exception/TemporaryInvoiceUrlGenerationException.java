package com.jardvcode.erp.filestorage.exception;

import com.jardvcode.erp.shared.domain.DomainError;

public final class TemporaryInvoiceUrlGenerationException extends DomainError {
    public TemporaryInvoiceUrlGenerationException() {
        super("No se pudo generar la URL temporal para la factura");
    }
}
