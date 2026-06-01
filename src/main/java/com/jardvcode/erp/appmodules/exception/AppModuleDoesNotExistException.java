package com.jardvcode.erp.appmodules.exception;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class AppModuleDoesNotExistException extends DomainError {

    private static final String message = "El modulo no existe.";

    public AppModuleDoesNotExistException(DomainErrorType domainErrorType) {
        super(domainErrorType, message);
    }

    public AppModuleDoesNotExistException() {
        super(message);
    }

}
