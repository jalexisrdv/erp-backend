package com.erp.role.exception;

import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.DomainErrorType;

public final class RoleDoesNotExistException extends DomainError {
    private final static String message = "El rol no existe.";

    public RoleDoesNotExistException(DomainErrorType errorType) {
        super(errorType, message);
    }

    public RoleDoesNotExistException() {
        super(message);
    }
}
