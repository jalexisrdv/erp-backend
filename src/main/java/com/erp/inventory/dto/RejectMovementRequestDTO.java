package com.erp.inventory.dto;

public record RejectMovementRequestDTO(
        Long id,
        Long itemId,
        String reason
) {
}
