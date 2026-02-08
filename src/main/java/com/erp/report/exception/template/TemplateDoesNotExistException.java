package com.erp.report.exception.template;

import com.erp.shared.domain.DomainError;

public final class TemplateDoesNotExistException extends DomainError {
    public TemplateDoesNotExistException() {
        super("La plantilla no existe.");
    }
}
