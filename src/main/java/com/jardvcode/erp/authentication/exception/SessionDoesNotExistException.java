package com.jardvcode.erp.authentication.exception;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class SessionDoesNotExistException extends DomainError {
    public SessionDoesNotExistException() {
        super(DomainErrorType.NOT_FOUND, "La sesión no existe");
    }
}
