package com.jardvcode.erp.checklists.exception.template;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class TemplateAlreadyExistsException extends DomainError {
    private static final String message = "El nombre de plantilla ya está en uso.";

    public TemplateAlreadyExistsException(DomainErrorType domainErrorType) {
        super(domainErrorType, message);
    }

    public TemplateAlreadyExistsException() {
        super(message);
    }
}
