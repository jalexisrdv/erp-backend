package com.jardvcode.erp.authorization.exception;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class RoleDoesNotExistException extends DomainError {
    private final static String message = "El rol no existe.";

    public RoleDoesNotExistException(DomainErrorType errorType) {
        super(errorType, message);
    }

    public RoleDoesNotExistException() {
        super(message);
    }
}
