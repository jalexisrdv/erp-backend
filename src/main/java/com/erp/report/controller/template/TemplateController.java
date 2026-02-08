package com.erp.report.controller.template;

import com.erp.report.dto.template.TemplateCatalogDTO;
import com.erp.report.dto.template.TemplateDTO;
import com.erp.report.mapper.template.TemplateCatalogMapper;
import com.erp.report.mapper.template.TemplateMapper;
import com.erp.report.service.template.TemplateCrud;
import com.erp.shared.domain.ResponseWrapper;
import com.erp.shared.dto.pagination.PaginationRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "reports/templates")
public final class TemplateController {

    private final TemplateCrud crud;
    private final TemplateMapper mapper;
    private final TemplateCatalogMapper catalogMapper;

    public TemplateController(TemplateCrud crud) {
        this.crud = crud;
        this.mapper = new TemplateMapper();
        this.catalogMapper = new TemplateCatalogMapper();
    }

    @GetMapping(value = "/catalog")
    public ResponseEntity<ResponseWrapper<List<TemplateCatalogDTO>>> findAll() {
        return ResponseWrapper.ok(catalogMapper.fromEntity(crud.findAll()));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<TemplateDTO>> create(@RequestBody TemplateDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.create(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/pagination")
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<TemplateDTO>>> searchByPage(@RequestBody PaginationRequestDTO dto) {
        return ResponseWrapper.ok(mapper.fromPagination(crud.searchByPage(dto)));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<TemplateDTO>> update(@RequestBody TemplateDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.update(mapper.fromDTO(dto))));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponseWrapper<Void>> delete(@PathVariable Long id) {
        crud.deleteById(id);
        return ResponseWrapper.ok(null);
    }

}
