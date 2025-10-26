package com.erp.user.exception;

import com.erp.shared.domain.DomainError;

public final class UsernameAlreadyExistsException extends DomainError {
    public UsernameAlreadyExistsException(Integer httpCode) {
        super(httpCode, "El nombre de usuario ya está en uso.");
    }
}
