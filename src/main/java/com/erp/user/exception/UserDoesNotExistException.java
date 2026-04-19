package com.erp.user.exception;

import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.DomainErrorType;

public final class UserDoesNotExistException extends DomainError {
    private final static String message = "El usuario no existe.";

    public UserDoesNotExistException(DomainErrorType errorType) {
        super(errorType, message);
    }

    public UserDoesNotExistException() {
        super(message);
    }
}
