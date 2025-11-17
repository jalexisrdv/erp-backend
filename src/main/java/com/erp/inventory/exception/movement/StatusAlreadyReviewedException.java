package com.erp.inventory.exception.movement;

import com.erp.shared.domain.DomainError;

public final class StatusAlreadyReviewedException extends DomainError {
    public StatusAlreadyReviewedException() {
        super("Solo se puede aprobar o rechazar un movimiento que aún no ha sido revisado.");
    }
}
