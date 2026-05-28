package com.jardvcode.erp.inventory.dto;

public record RejectMovementRequestDTO(
        Long id,
        Long itemId,
        String reason
) {
}
