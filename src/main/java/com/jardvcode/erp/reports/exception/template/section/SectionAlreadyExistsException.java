package com.jardvcode.erp.reports.exception.template.section;

import com.jardvcode.erp.shared.domain.DomainError;

public final class SectionAlreadyExistsException extends DomainError {
    public SectionAlreadyExistsException() {
        super("El nombre de sección ya está en uso.");
    }
}
