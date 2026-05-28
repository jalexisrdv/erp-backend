package com.jardvcode.erp.reports.exception.assignment;

import com.jardvcode.erp.shared.domain.DomainError;

public final class AssigmentDoesNotExistException extends DomainError {
    public AssigmentDoesNotExistException() {
        super("La asignacion no existe.");
    }
}
