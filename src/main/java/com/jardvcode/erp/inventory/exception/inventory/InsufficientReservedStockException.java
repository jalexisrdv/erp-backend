package com.jardvcode.erp.inventory.exception.inventory;

import com.jardvcode.erp.shared.domain.DomainError;

public final class InsufficientReservedStockException extends DomainError {
    public InsufficientReservedStockException() {
        super("No se puede aprobar el movimiento porque el stock reservado es insuficiente para cubrir la cantidad solicitada.");
    }
}