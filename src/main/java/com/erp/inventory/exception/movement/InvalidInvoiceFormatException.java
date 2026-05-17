package com.erp.inventory.exception.movement;

import com.erp.shared.domain.DomainError;

public final class InvalidInvoiceFormatException extends DomainError {
    public InvalidInvoiceFormatException(String message) {
        super(message);
    }
}
