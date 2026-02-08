package com.erp.report.exception.assignment.response;

import com.erp.shared.domain.DomainError;

public final class CommentRequiredException extends DomainError {
    public CommentRequiredException() {
        super("El comentario es obligatorio cuando el estado no es OK.");
    }
}
