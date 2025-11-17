package com.erp.inventory.exception.movement;

import com.erp.shared.domain.DomainError;

public final class InvalidQuantityException extends DomainError {
    public InvalidQuantityException() {
        super("La cantidad debe ser mayor que 0.");
    }
}