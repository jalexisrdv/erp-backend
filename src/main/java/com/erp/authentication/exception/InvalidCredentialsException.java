package com.erp.authentication.exception;

import com.erp.shared.domain.DomainError;

public final class InvalidCredentialsException extends DomainError {
    public InvalidCredentialsException(Integer httpCode) {
        super(httpCode, "Usuario o contraseña incorrectos. Por favor, inténtalo de nuevo.");
    }
}
