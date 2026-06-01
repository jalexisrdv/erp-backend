package com.jardvcode.erp.checklists.exception.assignment;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class IncompleteTemplateException extends DomainError {
    private final static String message = "No puedes asignar una plantilla incompleta.";

    public IncompleteTemplateException(DomainErrorType errorType) {
        super(errorType, message);
    }

    public IncompleteTemplateException(String message) {
        super(message);
    }

}
