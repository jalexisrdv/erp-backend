package com.erp.report.controller.template;

import com.erp.report.dto.template.SectionDTO;
import com.erp.report.mapper.template.SectionMapper;
import com.erp.report.service.template.SectionCrud;
import com.erp.shared.domain.ResponseWrapper;
import com.erp.shared.dto.pagination.PaginationRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"reports/templates/sections"})
public final class SectionController {

    private final SectionCrud crud;
    private final SectionMapper mapper;

    public SectionController(SectionCrud crud) {
        this.crud = crud;
        this.mapper = new SectionMapper();
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<SectionDTO>> create(@RequestBody SectionDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.create(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/pagination")
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<SectionDTO>>> searchByPage(@RequestBody PaginationRequestDTO dto) {
        return ResponseWrapper.ok(mapper.fromPagination(crud.searchByPage(dto)));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<SectionDTO>> update(@RequestBody SectionDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.update(mapper.fromDTO(dto))));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponseWrapper<Void>> delete(@PathVariable Long id) {
        crud.deleteById(id);
        return ResponseWrapper.ok(null);
    }

}
