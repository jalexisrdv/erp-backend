package com.jardvcode.erp.users.exception;

import com.jardvcode.erp.shared.domain.DomainError;

public final class UsernameAlreadyExistsException extends DomainError {
    public UsernameAlreadyExistsException() {
        super("El nombre de usuario ya está en uso.");
    }
}
