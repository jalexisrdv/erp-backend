package com.erp.authorization.exception;

import com.erp.shared.domain.DomainError;

public final class PermissionDoesNotExistException extends DomainError {

    public PermissionDoesNotExistException() {
        super("El permiso no existe.");
    }

}
