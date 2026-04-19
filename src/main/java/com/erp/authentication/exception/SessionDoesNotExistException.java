package com.erp.authentication.exception;

import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.DomainErrorType;

public final class SessionDoesNotExistException extends DomainError {
    public SessionDoesNotExistException() {
        super(DomainErrorType.NOT_FOUND, "La sesión no existe");
    }
}
