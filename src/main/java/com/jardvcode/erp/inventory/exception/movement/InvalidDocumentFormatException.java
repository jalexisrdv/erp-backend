package com.jardvcode.erp.inventory.exception.movement;

import com.jardvcode.erp.shared.domain.DomainError;

public final class InvalidDocumentFormatException extends DomainError {
    public InvalidDocumentFormatException(String message) {
        super(message);
    }
}
