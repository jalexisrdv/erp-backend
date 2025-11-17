package com.erp.inventory.entity;

import com.erp.inventory.domain.MovementEnum;
import com.erp.inventory.domain.StatusEnum;
import com.erp.inventory.exception.inventory.InsufficientStockException;
import com.erp.inventory.exception.movement.*;
import com.erp.user.entity.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_movements")
public final class MovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inventory_id")
    private InventoryEntity item;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MovementEnum type;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "invoice_url")
    private String invoiceUrl;

    @Column(name = "output_reason")
    private String outputReason;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "rejected_reason")
    private String rejectReason;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "updated_by")
    private UserEntity updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reviewed_by")
    private UserEntity reviewedBy;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    public static MovementEntity fromPrimitives(Long id, Long inventoryId, String invoiceUrl, Long quantity, String outputReason, String rejectReason) {
        InventoryEntity inventory = new InventoryEntity();
        inventory.setId(inventoryId);

        MovementEntity entity = new MovementEntity();
        entity.id = id;
        entity.item = inventory;
        entity.invoiceUrl = invoiceUrl;
        entity.quantity = quantity;
        entity.outputReason = outputReason;
        entity.rejectReason = rejectReason;

        return entity;
    }

    public MovementEntity createEntry(UserEntity user, InventoryEntity item, Long quantity, String invoiceUrl) {
        if(quantity <= 0) {
            throw new InvalidQuantityException();
        }

        if(invoiceUrl == null || invoiceUrl.isEmpty()) {
            throw new InvoiceRequiredException();
        }

        this.item = item;
        type = MovementEnum.ENTRADA;
        this.quantity = quantity;
        status = StatusEnum.PENDIENTE;
        createdBy = user;
        createdAt = LocalDateTime.now();

        return this;
    }

    public void updateEntry(UserEntity user, Long quantity, String invoiceUrl) {
        if(status != StatusEnum.PENDIENTE) {
            throw new StatusAlreadyReviewedException();
        }

        if(quantity <= 0) {
            throw new InvalidQuantityException();
        }

        if(invoiceUrl == null || invoiceUrl.isEmpty()) {
            throw new InvoiceRequiredException();
        }

        this.quantity = quantity;
        updatedBy = user;
        updatedAt = LocalDateTime.now();
    }

    public void approveEntry(UserEntity user) {
        if(status != StatusEnum.PENDIENTE) {
            throw new StatusAlreadyReviewedException();
        }

        status = StatusEnum.APROBADO;
        reviewedBy = user;
        reviewedAt = LocalDateTime.now();
    }

    public MovementEntity createOutput(UserEntity user, InventoryEntity item, Long quantity, String reason) {
        if(quantity <= 0) {
            throw new InvalidQuantityException();
        }

        if(reason == null || reason.isEmpty()) {
            throw new OutputReasonRequiredException();
        }

        if(!item.hasStockFor(quantity)) {
            throw new InsufficientStockException();
        }

        this.item = item;
        type = MovementEnum.SALIDA;
        this.quantity = quantity;
        outputReason = reason;
        status = StatusEnum.PENDIENTE;
        createdBy = user;
        createdAt = LocalDateTime.now();

        return this;
    }

    public void updateOutput(UserEntity user, Long quantity, String reason) {
        if(status != StatusEnum.PENDIENTE) {
            throw new StatusAlreadyReviewedException();
        }

        if(quantity <= 0) {
            throw new InvalidQuantityException();
        }

        if(reason == null || reason.isEmpty()) {
            throw new OutputReasonRequiredException();
        }

        if(!item.hasStockFor(quantity)) {
            throw new InsufficientStockException();
        }

        this.quantity = quantity;
        outputReason = reason;
        updatedBy = user;
        updatedAt = LocalDateTime.now();
    }

    public void approveOutput(UserEntity user) {
        if(status != StatusEnum.PENDIENTE) {
            throw new StatusAlreadyReviewedException();
        }

        if(!item.hasStockFor(quantity)) {
            throw new InsufficientStockException();
        }

        status = StatusEnum.APROBADO;
        reviewedBy = user;
        reviewedAt = LocalDateTime.now();
    }

    public void reject(UserEntity user, String reason) {
        if(status != StatusEnum.PENDIENTE) {
            throw new StatusAlreadyReviewedException();
        }

        if(reason == null || reason.isEmpty()) {
            throw new RejectReasonRequiredException();
        }

        rejectReason = reason;
        status = StatusEnum.RECHAZADO;
        reviewedBy = user;
        reviewedAt = LocalDateTime.now();
    }

    public String createdBy() {
        if(createdBy == null) {
            return "";
        }
        return createdBy.getUsername();
    }

    public String reviewedBy() {
        if(reviewedBy == null) {
            return "";
        }
        return createdBy.getUsername();
    }

    public String updatedBy() {
        if(updatedBy == null) {
            return "";
        }
        return createdBy.getUsername();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InventoryEntity getItem() {
        return item;
    }

    public void setItem(InventoryEntity item) {
        this.item = item;
    }

    public MovementEnum getType() {
        return type;
    }

    public void setType(MovementEnum type) {
        this.type = type;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getInvoiceUrl() {
        return invoiceUrl;
    }

    public void setInvoiceUrl(String invoiceUrl) {
        this.invoiceUrl = invoiceUrl;
    }

    public String getOutputReason() {
        return outputReason;
    }

    public void setOutputReason(String outputReason) {
        this.outputReason = outputReason;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserEntity getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(UserEntity reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

}
