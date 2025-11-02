package com.erp.inventory.controller;

import com.erp.inventory.dto.InventoryDTO;
import com.erp.inventory.mapper.InventoryMapper;
import com.erp.inventory.service.InventoryCrud;
import com.erp.shared.domain.ResponseWrapper;
import com.erp.shared.dto.pagination.PaginationRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "inventory")
public final class InventoryController {

    private final InventoryCrud crud;
    private final InventoryMapper mapper;

    public InventoryController(InventoryCrud crud) {
        this.crud = crud;
        this.mapper = new InventoryMapper();
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<InventoryDTO>> create(@RequestBody InventoryDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.create(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/pagination")
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<InventoryDTO>>> searchByPage(@RequestBody PaginationRequestDTO dto) {
        return ResponseWrapper.ok(mapper.fromPagination(crud.searchByPage(dto)));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<InventoryDTO>> update(@RequestBody InventoryDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.update(mapper.fromDTO(dto))));
    }

}
