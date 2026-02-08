package com.erp.report.controller.template;

import com.erp.report.dto.template.ItemDTO;
import com.erp.report.mapper.template.ItemMapper;
import com.erp.report.service.template.ItemCrud;
import com.erp.shared.domain.ResponseWrapper;
import com.erp.shared.dto.pagination.PaginationRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "reports/templates/sections/items")
public final class ItemController {

    private final ItemCrud crud;
    private final ItemMapper mapper;

    public ItemController(ItemCrud crud) {
        this.crud = crud;
        this.mapper = new ItemMapper();
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ItemDTO>> create(@RequestBody ItemDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.create(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/pagination")
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<ItemDTO>>> searchByPage(@RequestBody PaginationRequestDTO dto) {
        return ResponseWrapper.ok(mapper.fromPagination(crud.searchByPage(dto)));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<ItemDTO>> update(@RequestBody ItemDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.update(mapper.fromDTO(dto))));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponseWrapper<Void>> delete(@PathVariable Long id) {
        crud.deleteById(id);
        return ResponseWrapper.ok(null);
    }

}
