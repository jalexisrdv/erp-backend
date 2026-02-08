package com.erp.report.exception.template;

import com.erp.shared.domain.DomainError;

public final class TemplateAlreadyExistsException extends DomainError {
    public TemplateAlreadyExistsException() {
        super("El nombre de plantilla ya está en uso.");
    }
}
