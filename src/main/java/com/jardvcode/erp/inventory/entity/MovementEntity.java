package com.jardvcode.erp.inventory.entity;

import com.jardvcode.erp.inventory.domain.MovementEnum;
import com.jardvcode.erp.inventory.domain.StatusEnum;
import com.jardvcode.erp.inventory.exception.inventory.InsufficientReservedStockException;
import com.jardvcode.erp.inventory.exception.inventory.InsufficientStockException;
import com.jardvcode.erp.inventory.exception.movement.*;
import com.jardvcode.erp.users.entity.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

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

    public static MovementEntity createEntry(Long itemId, Long quantity, String invoiceUrl, Long userId) {
        if(quantity <= 0) {
            throw new InvalidQuantityException();
        }

        if(invoiceUrl == null || invoiceUrl.isBlank()) {
            throw new InvoiceRequiredException();
        }

        InventoryEntity item = new InventoryEntity();
        item.setId(itemId);

        UserEntity user = new UserEntity();
        user.setId(userId);

        MovementEntity entity = new MovementEntity();

        entity.item = item;
        entity.type = MovementEnum.ENTRADA;
        entity.quantity = quantity;
        entity.invoiceUrl = invoiceUrl;
        entity.status = StatusEnum.PENDIENTE;
        entity.createdBy = user;
        entity.createdAt = LocalDateTime.now();

        return entity;
    }

    public static MovementEntity createApproved(Long id, Long itemId, Long userId) {
        InventoryEntity item = new InventoryEntity();
        item.setId(itemId);

        UserEntity user = new UserEntity();
        user.setId(userId);

        MovementEntity entity = new MovementEntity();

        entity.id = id;
        entity.item = item;
        entity.reviewedBy = user;

        return entity;
    }

    public static MovementEntity createRejected(Long id, Long itemId, String reason, Long userId) {
        InventoryEntity item = new InventoryEntity();
        item.setId(itemId);

        UserEntity user = new UserEntity();
        user.setId(userId);

        MovementEntity entity = new MovementEntity();

        entity.id = id;
        entity.item = item;
        entity.reviewedBy = user;
        entity.rejectReason = reason;

        return entity;
    }

    public void updateEntry(Long quantity, String invoiceUrl, Long userId) {
        if(status != StatusEnum.PENDIENTE) {
            throw new StatusAlreadyReviewedException();
        }

        if(quantity <= 0) {
            throw new InvalidQuantityException();
        }

        if(invoiceUrl == null || invoiceUrl.isEmpty()) {
            throw new InvoiceRequiredException();
        }

        UserEntity user = new UserEntity();
        user.setId(userId);

        this.quantity = quantity;
        updatedBy = user;
        updatedAt = LocalDateTime.now();
    }

    public void approveEntry(Long userId) {
        if(status != StatusEnum.PENDIENTE) {
            throw new StatusAlreadyReviewedException();
        }

        UserEntity user = new UserEntity();
        user.setId(userId);

        status = StatusEnum.APROBADO;
        reviewedBy = user;
        reviewedAt = LocalDateTime.now();
    }

    public static MovementEntity createOutput(Long itemId, Long quantity, String reason, Long userId) {
        if(quantity <= 0) {
            throw new InvalidQuantityException();
        }

        if(reason == null || reason.isEmpty()) {
            throw new OutputReasonRequiredException();
        }

        InventoryEntity item = new InventoryEntity();
        item.setId(itemId);

        UserEntity user = new UserEntity();
        user.setId(userId);

        MovementEntity entity = new MovementEntity();

        entity.item = item;
        entity.type = MovementEnum.SALIDA;
        entity.quantity = quantity;
        entity.outputReason = reason;
        entity.status = StatusEnum.PENDIENTE;
        entity.createdBy = user;
        entity.createdAt = LocalDateTime.now();

        return entity;
    }

    public void updateOutput(Long quantity, String reason, Long userId) {
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

        UserEntity user = new UserEntity();
        user.setId(userId);

        this.quantity = quantity;
        outputReason = reason;
        updatedBy = user;
        updatedAt = LocalDateTime.now();
    }

    public void approveOutput(Long userId) {
        if(status != StatusEnum.PENDIENTE) {
            throw new StatusAlreadyReviewedException();
        }

        if (!item.hasReservedStockFor(quantity)) {
            throw new InsufficientReservedStockException();
        }

        UserEntity user = new UserEntity();
        user.setId(userId);

        status = StatusEnum.APROBADO;
        reviewedBy = user;
        reviewedAt = LocalDateTime.now();
    }

    public void reject(Long userId, String reason) {
        if(status != StatusEnum.PENDIENTE) {
            throw new StatusAlreadyReviewedException();
        }

        if(reason == null || reason.isEmpty()) {
            throw new RejectReasonRequiredException();
        }

        UserEntity user = new UserEntity();
        user.setId(userId);

        rejectReason = reason;
        status = StatusEnum.RECHAZADO;
        reviewedBy = user;
        reviewedAt = LocalDateTime.now();
    }

    public static void ensureInvoicePdfFormat(String filename, byte[] content) throws InvalidInvoiceFormatException {
        if(filename == null || filename.isBlank()) {
            throw new InvalidInvoiceFormatException("No se pudo determinar el nombre del archivo de la factura o este se encuentra vacío");
        }

        if (content == null) {
            throw new InvalidInvoiceFormatException("El contenido del archivo de la factura no puede estar vacío");
        }

        if (content.length < 5) {
            throw new InvalidInvoiceFormatException("El archivo de la factura parece estar dañado o incompleto");
        }

        boolean isPdf = content[0] == 0x25 &&
                content[1] == 0x50 &&
                content[2] == 0x44 &&
                content[3] == 0x46 &&
                content[4] == 0x2D;

        if (!isPdf) {
            throw new InvalidInvoiceFormatException("El archivo no tiene una estructura de PDF válida");
        }
    }

    public static String generateInvoicePath(Long itemId) {
        return itemId + "/" + UUID.randomUUID() + ".pdf";
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
