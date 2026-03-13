package com.erp.report.exception.template;

import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.DomainErrorType;

public final class TemplateDoesNotExistException extends DomainError {
    public TemplateDoesNotExistException() {
        super("La plantilla no existe.");
    }

    public TemplateDoesNotExistException(DomainErrorType type) {
        super(type, "La plantilla no existe.");
    }
}
