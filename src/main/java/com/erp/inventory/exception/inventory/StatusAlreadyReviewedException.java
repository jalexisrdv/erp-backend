package com.erp.inventory.exception.inventory;

import com.erp.shared.domain.DomainError;

public final class StatusAlreadyReviewedException extends DomainError {
    public StatusAlreadyReviewedException(Integer httpCode) {
        super(httpCode, "Solo se puede aprobar o rechazar un movimiento que aún no ha sido revisado.");
    }
}
