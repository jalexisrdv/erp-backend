package com.jardvcode.erp.authorization.exception;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class PermissionDoesNotExistException extends DomainError {

    private static final String message = "El permiso no existe.";

    public PermissionDoesNotExistException(DomainErrorType domainErrorType) {
        super(domainErrorType, message);
    }

    public PermissionDoesNotExistException() {
        super(message);
    }

}
