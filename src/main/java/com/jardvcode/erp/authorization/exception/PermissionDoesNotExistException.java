package com.jardvcode.erp.authorization.exception;

import com.jardvcode.erp.shared.domain.DomainError;

public final class PermissionDoesNotExistException extends DomainError {

    public PermissionDoesNotExistException() {
        super("El permiso no existe.");
    }

}
