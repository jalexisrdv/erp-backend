package com.jardvcode.erp.reports.exception.template;

import com.jardvcode.erp.shared.domain.DomainError;

public final class TemplateAlreadyExistsException extends DomainError {
    public TemplateAlreadyExistsException() {
        super("El nombre de plantilla ya está en uso.");
    }
}
