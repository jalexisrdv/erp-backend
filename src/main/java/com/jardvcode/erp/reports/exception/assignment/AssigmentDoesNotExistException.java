package com.jardvcode.erp.reports.exception.assignment;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class AssigmentDoesNotExistException extends DomainError {
    private static final String message = "La asignacion no existe.";

    public AssigmentDoesNotExistException(DomainErrorType domainErrorType) {
        super(domainErrorType, message);
    }

    public AssigmentDoesNotExistException() {
        super(message);
    }
}
