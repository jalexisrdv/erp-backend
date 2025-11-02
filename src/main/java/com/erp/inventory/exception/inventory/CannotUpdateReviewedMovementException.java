package com.erp.inventory.exception.inventory;

import com.erp.shared.domain.DomainError;

public final class CannotUpdateReviewedMovementException extends DomainError {
    public CannotUpdateReviewedMovementException(Integer httpCode) {
        super(httpCode, "No es posible actualizar un movimiento que ya fue revisado.");
    }
}