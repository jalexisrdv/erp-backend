package com.erp.authentication.exception;

import com.erp.shared.domain.DomainError;

public final class InvalidPasswordResetTokenException extends DomainError {
    public InvalidPasswordResetTokenException(Integer httpCode) {
        super(httpCode, "Enlace de restablecimiento de contraseña inválido. Por favor, solicita uno nuevo.");
    }
}
