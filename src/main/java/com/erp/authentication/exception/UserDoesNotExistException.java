package com.erp.authentication.exception;

import com.erp.shared.domain.DomainError;

public final class UserDoesNotExistException extends DomainError {
    public UserDoesNotExistException(Integer httpCode) {
        super(httpCode, "El usuario no existe.");
    }
}
