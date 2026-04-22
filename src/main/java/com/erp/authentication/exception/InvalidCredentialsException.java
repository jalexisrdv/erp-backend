package com.erp.authentication.exception;

import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.DomainErrorType;

public final class InvalidCredentialsException extends DomainError {
    public InvalidCredentialsException() {
        super(DomainErrorType.FORBIDDEN, "Usuario o contraseña incorrectos. Por favor, inténtalo de nuevo.");
    }
}
