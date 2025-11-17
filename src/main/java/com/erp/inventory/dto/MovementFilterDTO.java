package com.erp.inventory.dto;

public record MovementFilterDTO(
        Long articleId,
        String status
) {

    public boolean hasArticleId() {
        return articleId != null;
    }

    public boolean hasStatus() {
        return status != null && !status.isEmpty();
    }

}
