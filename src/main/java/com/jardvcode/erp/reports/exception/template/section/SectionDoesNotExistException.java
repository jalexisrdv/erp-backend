package com.jardvcode.erp.reports.exception.template.section;

import com.jardvcode.erp.shared.domain.DomainError;

public final class SectionDoesNotExistException extends DomainError {
    public SectionDoesNotExistException() {
        super("La sección no existe.");
    }
}
