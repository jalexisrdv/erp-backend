package com.jardvcode.erp.authentication.exception;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class InvalidCredentialsException extends DomainError {
    public InvalidCredentialsException() {
        super(DomainErrorType.FORBIDDEN, "Usuario o contraseña incorrectos. Por favor, inténtalo de nuevo.");
    }
}
