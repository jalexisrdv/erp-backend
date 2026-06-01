package com.jardvcode.erp.checklists.exception.assignment.response;

import com.jardvcode.erp.shared.domain.DomainError;

public final class ResponseAlreadyExistsException extends DomainError {
    public ResponseAlreadyExistsException() {
        super("Ya existe una respuesta asociada al reporte.");
    }
}
