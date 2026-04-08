package com.erp.appmodule.exception;

import com.erp.shared.domain.DomainError;

public final class AppModuleDoesNotExistException extends DomainError {

    public AppModuleDoesNotExistException() {
        super("El modulo no existe.");
    }

}
