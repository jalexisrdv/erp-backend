package com.erp.inventory.exception.inventory;

import com.erp.shared.domain.DomainError;

public final class InsufficientStockException extends DomainError {
    public InsufficientStockException() {
        super("No hay suficiente stock o todo el inventario ya ha sido reservado.");
    }
}