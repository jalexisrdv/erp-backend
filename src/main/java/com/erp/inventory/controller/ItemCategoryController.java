package com.erp.inventory.controller;

import com.erp.inventory.dto.ItemCategoryDTO;
import com.erp.inventory.mapper.ItemCategoryMapper;
import com.erp.inventory.service.ItemCategoryCrud;
import com.erp.shared.domain.ResponseWrapper;
import com.erp.shared.dto.pagination.PaginationRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "inventory/categories")
public final class ItemCategoryController {

    private final ItemCategoryCrud crud;
    private final ItemCategoryMapper mapper;

    public ItemCategoryController(ItemCategoryCrud crud) {
        this.crud = crud;
        this.mapper = new ItemCategoryMapper();
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ItemCategoryDTO>> create(@RequestBody ItemCategoryDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.create(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/pagination")
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<ItemCategoryDTO>>> searchByPage(@RequestBody PaginationRequestDTO dto) {
        return ResponseWrapper.ok(mapper.fromPagination(crud.searchByPage(dto)));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<ItemCategoryDTO>> update(@RequestBody ItemCategoryDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.update(mapper.fromDTO(dto))));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteById(@PathVariable Long id) {
        crud.deleteById(id);
        return ResponseWrapper.ok(null);
    }

}
