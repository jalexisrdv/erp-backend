package com.jardvcode.erp.checklists.exception.assignment.response;

import com.jardvcode.erp.shared.domain.DomainError;

public final class ResponseDoNotExistException extends DomainError {
    public ResponseDoNotExistException() {
        super("Las respuesta no existe");
    }
}
