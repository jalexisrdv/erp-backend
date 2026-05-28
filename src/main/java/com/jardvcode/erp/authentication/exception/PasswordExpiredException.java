package com.jardvcode.erp.authentication.exception;

import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.shared.domain.DomainErrorType;

public final class PasswordExpiredException extends DomainError {
    public PasswordExpiredException(String username) {
        super(DomainErrorType.FORBIDDEN, "CHANGE_PASSWORD", "Las credenciales para el usuario " + username + " son válidas, pero han expirado. Se requiere un cambio de contraseña obligatorio para continuar.");
    }
}
