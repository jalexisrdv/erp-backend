package com.jardvcode.erp.reports.exception.template;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class TemplateDoesNotExistException extends DomainError {
    public TemplateDoesNotExistException() {
        super("La plantilla no existe.");
    }

    public TemplateDoesNotExistException(DomainErrorType type) {
        super(type, "La plantilla no existe.");
    }
}
