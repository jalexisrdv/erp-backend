package com.jardvcode.erp.users.exception;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class UsernameAlreadyExistsException extends DomainError {
    private static final String message = "El nombre de usuario ya está en uso.";

    public UsernameAlreadyExistsException(DomainErrorType domainErrorType) {
        super(domainErrorType, message);
    }

    public UsernameAlreadyExistsException() {
        super(message);
    }
}
