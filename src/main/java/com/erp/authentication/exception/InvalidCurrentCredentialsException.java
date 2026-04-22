package com.erp.authentication.exception;

import com.erp.shared.domain.DomainError;
import com.erp.shared.domain.DomainErrorType;

public final class InvalidCurrentCredentialsException extends DomainError {
    public InvalidCurrentCredentialsException() {
        super(DomainErrorType.FORBIDDEN, "Usuario o contraseña actual incorrectos. Por favor, inténtalo de nuevo.");
    }
}
