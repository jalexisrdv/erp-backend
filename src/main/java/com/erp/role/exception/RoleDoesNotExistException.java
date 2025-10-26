package com.erp.role.exception;

import com.erp.shared.domain.DomainError;

public final class RoleDoesNotExistException extends DomainError {
    public RoleDoesNotExistException(Integer httpCode) {
        super(httpCode, "El rol no existe.");
    }
}
