package com.jardvcode.erp.appmodules.exception;

import com.jardvcode.erp.shared.domain.DomainError;

public final class AppModuleDoesNotExistException extends DomainError {

    public AppModuleDoesNotExistException() {
        super("El modulo no existe.");
    }

}
