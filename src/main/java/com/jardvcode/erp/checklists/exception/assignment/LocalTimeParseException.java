package com.jardvcode.erp.checklists.exception.assignment;

import com.jardvcode.erp.shared.domain.DomainError;

public final class LocalTimeParseException extends DomainError {
    public LocalTimeParseException(String timeIn) {
        super("El formato de la hora " + timeIn + " es inválido.");
    }
}
