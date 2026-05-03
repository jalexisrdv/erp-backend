package com.erp.report.controller.template;

import com.erp.report.dto.template.*;
import com.erp.report.mapper.template.TemplateCatalogMapper;
import com.erp.report.mapper.template.TemplateMapper;
import com.erp.report.mapper.template.TemplateStructureMapper;
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
    private final TemplateMapper templateMapper;
    private final TemplateStructureMapper templateStructureMapper;
    private final TemplateCatalogMapper catalogMapper;

    public TemplateController(TemplateCrud crud) {
        this.crud = crud;
        templateMapper = new TemplateMapper();
        templateStructureMapper = new TemplateStructureMapper();
        catalogMapper = new TemplateCatalogMapper();
    }

    @GetMapping(value = "/catalog")
    public ResponseEntity<ResponseWrapper<List<TemplateCatalogDTO>>> findAll() {
        return ResponseWrapper.ok(catalogMapper.fromEntity(crud.findAll()));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<TemplateDTO>> create(@RequestBody TemplateDTO dto) {
        return ResponseWrapper.ok(templateMapper.fromEntity(crud.create(templateMapper.fromDTO(dto))));
    }

    @PostMapping(value = "/pagination")
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<TemplateDTO>>> searchByPage(@RequestBody PaginationRequestDTO dto) {
        return ResponseWrapper.ok(templateMapper.fromPagination(crud.searchByPage(dto)));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<TemplateDTO>> update(@RequestBody TemplateDTO dto) {
        return ResponseWrapper.ok(templateMapper.fromEntity(crud.update(templateMapper.fromDTO(dto))));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponseWrapper<Void>> delete(@PathVariable Long id) {
        crud.deleteById(id);
        return ResponseWrapper.ok(null);
    }

    @GetMapping(value = "/structure")
    public ResponseEntity<ResponseWrapper<TemplateStructureResponseDTO>> fetchStructure(@RequestParam("id") Long id) {
        return ResponseWrapper.ok(templateStructureMapper.fromEntity(crud.findWithSectionsAndItemsById(id)));
    }

    @PutMapping(value = "/structure")
    public ResponseEntity<ResponseWrapper<List<SectionDTO>>> updateStructure(@RequestBody TemplateStructureRequestDTO dto) {
        crud.updateStructure(templateMapper.fromSectionDTO(dto.id(), dto.sections()));
        return ResponseWrapper.ok(null);
    }

}
