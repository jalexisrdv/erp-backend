package com.jardvcode.erp.authentication.exception;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class SessionExpiredException extends DomainError {
    public SessionExpiredException() {
        super(DomainErrorType.FORBIDDEN, "Su sesión ha expirado. Por favor, ingrese de nuevo.");
    }
}
