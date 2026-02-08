package com.erp.report.exception.template.section;

import com.erp.shared.domain.DomainError;

public final class SectionDoesNotExistException extends DomainError {
    public SectionDoesNotExistException() {
        super("La sección no existe.");
    }
}
