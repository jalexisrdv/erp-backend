package com.jardvcode.erp.inventory.controller;

import com.jardvcode.erp.inventory.dto.ItemCategoryDTO;
import com.jardvcode.erp.inventory.service.ItemCategoryCrud;
import com.jardvcode.erp.shared.domain.ResponseWrapper;
import com.jardvcode.erp.shared.dto.pagination.PaginationRequestDTO;
import com.jardvcode.erp.shared.dto.pagination.ResponsePaginationDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "inventory/categories")
public final class ItemCategoryController {

    private final ItemCategoryCrud crud;

    public ItemCategoryController(ItemCategoryCrud crud) {
        this.crud = crud;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ItemCategoryDTO>> create(@RequestBody ItemCategoryDTO dto) {
        return ResponseWrapper.ok(ItemCategoryDTO.fromEntity(crud.create(dto)));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<ResponseWrapper<List<ItemCategoryDTO>>> findAll() {
        return ResponseWrapper.ok(ItemCategoryDTO.fromEntities(crud.findAll()));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<ItemCategoryDTO>> update(@RequestBody ItemCategoryDTO dto) {
        return ResponseWrapper.ok(ItemCategoryDTO.fromEntity(crud.update(dto)));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteById(@PathVariable Long id) {
        crud.deleteById(id);
        return ResponseWrapper.ok(null);
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<ItemCategoryDTO>>> search(@Valid @RequestParam PaginationRequestDTO dto) {
        return ResponseWrapper.ok(ResponsePaginationDTO.create(crud.search(dto), ItemCategoryDTO::fromEntity));
    }

}
