package com.jardvcode.erp.inventory.exception.movement;

import com.jardvcode.erp.shared.domain.DomainError;

public final class OutputReasonRequiredException extends DomainError {
    public OutputReasonRequiredException() {
        super("La razón de salida es obligatoria.");
    }
}