package com.erp.report.exception.assignment.response;

import com.erp.shared.domain.DomainError;

public final class ResponseDoesNotExistException extends DomainError {
    public ResponseDoesNotExistException() {
        super("La respuesta no existe.");
    }
}
