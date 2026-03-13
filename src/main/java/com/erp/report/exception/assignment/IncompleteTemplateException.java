package com.erp.report.exception.assignment;

import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.DomainErrorType;

public final class IncompleteTemplateException extends DomainError {
    private final static String message = "No puedes asignar una plantilla incompleta.";

    public IncompleteTemplateException(DomainErrorType errorType) {
        super(errorType, message);
    }

    public IncompleteTemplateException(String message) {
        super(message);
    }

}
