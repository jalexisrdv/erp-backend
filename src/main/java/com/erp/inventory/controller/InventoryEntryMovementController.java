package com.erp.inventory.controller;

import com.erp.inventory.dto.InventoryEntryMovementDTO;
import com.erp.inventory.mapper.InventoryEntryMovementMapper;
import com.erp.inventory.service.InventoryEntryMovementCrud;
import com.erp.shared.domain.ResponseWrapper;
import com.erp.shared.dto.pagination.PaginationRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "inventory/entries")
public final class InventoryEntryMovementController {

    private final InventoryEntryMovementCrud crud;
    private final InventoryEntryMovementMapper mapper;

    public InventoryEntryMovementController(InventoryEntryMovementCrud crud) {
        this.crud = crud;
        this.mapper = new InventoryEntryMovementMapper();
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<InventoryEntryMovementDTO>> create(@RequestBody InventoryEntryMovementDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.create(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/pagination")
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<InventoryEntryMovementDTO>>> searchByPage(@RequestBody PaginationRequestDTO dto) {
        return ResponseWrapper.ok(mapper.fromPagination(crud.searchByPage(dto)));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<InventoryEntryMovementDTO>> update(@RequestBody InventoryEntryMovementDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.update(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/approve")
    public ResponseEntity<ResponseWrapper<InventoryEntryMovementDTO>> approve(@RequestBody InventoryEntryMovementDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.approve(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/reject")
    public ResponseEntity<ResponseWrapper<InventoryEntryMovementDTO>> reject(@RequestBody InventoryEntryMovementDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.reject(mapper.fromDTO(dto))));
    }

}
