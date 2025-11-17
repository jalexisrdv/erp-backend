package com.erp.inventory.exception.movement;

import com.erp.shared.domain.DomainError;

public final class OutputReasonRequiredException extends DomainError {
    public OutputReasonRequiredException() {
        super("La razón de salida es obligatoria.");
    }
}