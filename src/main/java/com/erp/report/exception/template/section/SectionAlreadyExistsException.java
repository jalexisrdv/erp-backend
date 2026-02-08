package com.erp.report.exception.template.section;

import com.erp.shared.domain.DomainError;

public final class SectionAlreadyExistsException extends DomainError {
    public SectionAlreadyExistsException() {
        super("El nombre de sección ya está en uso.");
    }
}
