package com.erp.inventory.controller;

import com.erp.inventory.dto.InventoryOutputMovementDTO;
import com.erp.inventory.mapper.InventoryOutputMovementMapper;
import com.erp.inventory.service.InventoryOutputMovementCrud;
import com.erp.shared.domain.ResponseWrapper;
import com.erp.shared.dto.pagination.PaginationRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "inventory/outputs")
public final class InventoryOutputMovementController {

    private final InventoryOutputMovementCrud crud;
    private final InventoryOutputMovementMapper mapper;

    public InventoryOutputMovementController(InventoryOutputMovementCrud crud) {
        this.crud = crud;
        this.mapper = new InventoryOutputMovementMapper();
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<InventoryOutputMovementDTO>> create(@RequestBody InventoryOutputMovementDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.create(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/pagination")
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<InventoryOutputMovementDTO>>> searchByPage(@RequestBody PaginationRequestDTO dto) {
        return ResponseWrapper.ok(mapper.fromPagination(crud.searchByPage(dto)));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<InventoryOutputMovementDTO>> update(@RequestBody InventoryOutputMovementDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.update(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/approve")
    public ResponseEntity<ResponseWrapper<InventoryOutputMovementDTO>> approve(@RequestBody InventoryOutputMovementDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.approve(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/reject")
    public ResponseEntity<ResponseWrapper<InventoryOutputMovementDTO>> reject(@RequestBody InventoryOutputMovementDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.reject(mapper.fromDTO(dto))));
    }

}
