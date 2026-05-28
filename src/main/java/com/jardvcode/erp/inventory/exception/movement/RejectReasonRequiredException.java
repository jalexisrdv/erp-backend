package com.jardvcode.erp.inventory.exception.movement;

import com.jardvcode.erp.shared.domain.DomainError;

public final class RejectReasonRequiredException extends DomainError {
    public RejectReasonRequiredException() {
        super("La razón de rechazo es obligatoria.");
    }
}