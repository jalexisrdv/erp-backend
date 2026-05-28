package com.jardvcode.erp.inventory.exception.movement;

import com.jardvcode.erp.shared.domain.DomainError;

public final class InvalidQuantityException extends DomainError {
    public InvalidQuantityException() {
        super("La cantidad debe ser mayor que 0.");
    }
}