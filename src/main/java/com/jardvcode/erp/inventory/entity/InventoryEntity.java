package com.jardvcode.erp.inventory.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
public final class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_category_id")
    private ItemCategoryEntity category;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "minimum_stock")
    private Integer minimumStock;

    @Column(name = "entry_count")
    private Long entryCount;

    @Column(name = "pending_entry_count")
    private Long pendingEntryCount;

    @Column(name = "output_count")
    private Long outputCount;

    @Column(name = "reserved_output_count")
    private Long reservedOutputCount;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static InventoryEntity create(Long id, ItemCategoryEntity category, String itemCode, String itemName, Integer minimumStock, Long userId) {
        InventoryEntity entity = new InventoryEntity();

        entity.id = id;
        entity.category = category;
        entity.itemCode = itemCode;
        entity.itemName = itemName;
        entity.minimumStock = minimumStock;
        entity.entryCount = 0L;
        entity.outputCount = 0L;
        entity.pendingEntryCount = 0L;
        entity.reservedOutputCount = 0L;
        entity.createdBy = userId;
        entity.createdAt = LocalDateTime.now();

        return entity;
    }

    public void update(ItemCategoryEntity category, String code, String name, Integer minimumStock, Long userId) {
        this.category = category;

        itemCode = code;
        itemName = name;
        this.minimumStock = minimumStock;
        updatedBy = userId;
        updatedAt = LocalDateTime.now();
    }

    public boolean hasStockFor(Long quantity) {
        return quantity <= stock();
    }

    public boolean hasReservedStockFor(Long quantity) {
        return quantity <= reservedOutputCount;
    }

    public Long stock() {
        return entryCount - outputCount - reservedOutputCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(ItemCategoryEntity category) {
        this.category = category;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(Integer minimumStock) {
        this.minimumStock = minimumStock;
    }

    public Long getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(Long entryCount) {
        this.entryCount = entryCount;
    }

    public Long getPendingEntryCount() {
        return pendingEntryCount;
    }

    public void setPendingEntryCount(Long pendingEntryCount) {
        this.pendingEntryCount = pendingEntryCount;
    }

    public Long getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(Long outputCount) {
        this.outputCount = outputCount;
    }

    public Long getReservedOutputCount() {
        return reservedOutputCount;
    }

    public void setReservedOutputCount(Long reservedOutputCount) {
        this.reservedOutputCount = reservedOutputCount;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
