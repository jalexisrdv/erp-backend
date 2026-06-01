package com.jardvcode.erp.checklists.exception.assignment.response;

import com.jardvcode.erp.shared.domain.DomainError;

public final class CommentRequiredException extends DomainError {
    public CommentRequiredException() {
        super("El comentario es obligatorio cuando el estado no es OK.");
    }
}
