package com.erp.inventory.exception.movement;

import com.erp.shared.domain.DomainError;

public final class RejectReasonRequiredException extends DomainError {
    public RejectReasonRequiredException() {
        super("La razón de rechazo es obligatoria.");
    }
}