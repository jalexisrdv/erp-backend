package com.erp.authentication.exception;

import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.DomainErrorType;

public final class SessionExpiredException extends DomainError {
    public SessionExpiredException() {
        super(DomainErrorType.FORBIDDEN, "Su sesión ha expirado. Por favor, ingrese de nuevo.");
    }
}
