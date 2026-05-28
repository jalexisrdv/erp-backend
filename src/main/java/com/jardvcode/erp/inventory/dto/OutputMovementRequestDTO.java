package com.jardvcode.erp.inventory.dto;

public record OutputMovementRequestDTO(
        Long id,
        Long itemId,
        Long quantity,
        String reason
) {
}
