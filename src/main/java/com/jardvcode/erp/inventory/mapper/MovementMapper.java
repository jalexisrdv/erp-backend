package com.jardvcode.erp.inventory.mapper;

import com.jardvcode.erp.inventory.dto.ApproveMovementRequestDTO;
import com.jardvcode.erp.inventory.dto.InvoicePreviewDTO;
import com.jardvcode.erp.inventory.dto.MovementDTO;
import com.jardvcode.erp.inventory.dto.RejectMovementRequestDTO;
import com.jardvcode.erp.inventory.entity.MovementEntity;
import com.jardvcode.erp.shared.dto.pagination.ResponsePaginationDTO;

import java.util.List;

public final class MovementMapper {

    private final InventoryMapper inventoryMapper;

    public MovementMapper() {
        this.inventoryMapper = new InventoryMapper();
    }

    public MovementEntity toEntity(ApproveMovementRequestDTO dto, Long userId) {
        return MovementEntity.createApproved(
                dto.id(),
                dto.itemId(),
                userId
        );
    }

    public MovementEntity toEntity(RejectMovementRequestDTO dto, Long userId) {
        return MovementEntity.createRejected(
                dto.id(),
                dto.itemId(),
                dto.reason(),
                userId
        );
    }

    public MovementDTO toDTO(MovementEntity entity) {
        return new MovementDTO(
                entity.getId(),
                inventoryMapper.fromEntity(entity.getItem()),
                entity.getType(),
                entity.getQuantity(),
                entity.getInvoiceUrl(),
                entity.getOutputReason(),
                entity.getStatus().name(),
                entity.getRejectReason(),
                entity.createdBy(),
                entity.getCreatedAt(),
                entity.updatedBy(),
                entity.getUpdatedAt(),
                entity.reviewedBy(),
                entity.getReviewedAt()
        );
    }

    public ResponsePaginationDTO<MovementDTO> fromPagination(ResponsePaginationDTO<MovementEntity> pagination) {
        List<MovementDTO> data = pagination.data().stream().map((entity) -> toDTO(entity)).toList();

        return ResponsePaginationDTO.create(
                pagination.page().number(),
                pagination.page().size(),
                pagination.page().pages(),
                pagination.page().items(),
                data
        );
    }

    public InvoicePreviewDTO toDTO(String url) {
        return new InvoicePreviewDTO(url);
    }

}
