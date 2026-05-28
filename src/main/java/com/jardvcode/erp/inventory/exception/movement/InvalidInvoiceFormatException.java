package com.jardvcode.erp.inventory.exception.movement;

import com.jardvcode.erp.shared.domain.DomainError;

public final class InvalidInvoiceFormatException extends DomainError {
    public InvalidInvoiceFormatException(String message) {
        super(message);
    }
}
