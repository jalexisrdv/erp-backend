package com.jardvcode.erp.reports.exception.assignment.response;

import com.jardvcode.erp.shared.domain.DomainError;

public final class ResponseDoNotExistException extends DomainError {
    public ResponseDoNotExistException() {
        super("Las respuesta no existe");
    }
}
