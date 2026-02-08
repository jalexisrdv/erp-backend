package com.erp.report.exception.assignment.response;

import com.erp.shared.domain.DomainError;

public final class ResponseAlreadyExistsException extends DomainError {
    public ResponseAlreadyExistsException() {
        super("Ya existe una respuesta asociada al reporte.");
    }
}
