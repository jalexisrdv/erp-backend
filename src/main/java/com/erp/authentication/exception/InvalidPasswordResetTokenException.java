package com.erp.authentication.exception;

import com.erp.shared.domain.DomainError;

public final class InvalidPasswordResetTokenException extends DomainError {
    public InvalidPasswordResetTokenException() {
        super("Enlace de restablecimiento de contraseña inválido. Por favor, solicita uno nuevo.");
    }
}
