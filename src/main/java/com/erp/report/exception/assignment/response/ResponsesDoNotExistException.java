package com.erp.report.exception.assignment.response;

import com.erp.shared.domain.DomainError;

import java.util.List;

public final class ResponsesDoNotExistException extends DomainError {
    public ResponsesDoNotExistException(List<Long> ids) {
        super("Las respuestas no existen " + ids.toString());
    }
}
