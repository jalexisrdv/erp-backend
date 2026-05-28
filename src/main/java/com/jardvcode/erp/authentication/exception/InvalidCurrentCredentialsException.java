package com.jardvcode.erp.authentication.exception;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class InvalidCurrentCredentialsException extends DomainError {
    public InvalidCurrentCredentialsException() {
        super(DomainErrorType.FORBIDDEN, "Usuario o contraseña actual incorrectos. Por favor, inténtalo de nuevo.");
    }
}
