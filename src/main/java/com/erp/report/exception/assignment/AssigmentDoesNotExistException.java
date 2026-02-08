package com.erp.report.exception.assignment;

import com.erp.shared.domain.DomainError;

public final class AssigmentDoesNotExistException extends DomainError {
    public AssigmentDoesNotExistException() {
        super("La asignacion no existe.");
    }
}
