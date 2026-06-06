package com.jardvcode.erp.inventory.controller;

import com.jardvcode.erp.inventory.dto.InventoryDTO;
import com.jardvcode.erp.inventory.service.InventoryCrud;
import com.jardvcode.erp.shared.domain.ResponseWrapper;
import com.jardvcode.erp.shared.dto.pagination.PaginationRequestDTO;
import com.jardvcode.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "inventory")
public final class InventoryController {

    private final InventoryCrud crud;

    public InventoryController(InventoryCrud crud) {
        this.crud = crud;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<InventoryDTO>> create(@RequestBody InventoryDTO dto) {
        return ResponseWrapper.ok(InventoryDTO.fromEntity(crud.create(dto)));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<InventoryDTO>> update(@RequestBody InventoryDTO dto) {
        return ResponseWrapper.ok(InventoryDTO.fromEntity(crud.update(dto)));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<InventoryDTO>>> search(@ModelAttribute PaginationRequestDTO dto) {
        return ResponseWrapper.ok(ResponsePaginationDTO.create(crud.search(dto), InventoryDTO::fromEntity));
    }

}
