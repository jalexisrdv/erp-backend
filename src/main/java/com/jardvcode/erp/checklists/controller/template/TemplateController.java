package com.jardvcode.erp.checklists.controller.template;

import com.jardvcode.erp.checklists.dto.template.*;
import com.jardvcode.erp.checklists.service.template.TemplateCrud;
import com.jardvcode.erp.shared.domain.ResponseWrapper;
import com.jardvcode.erp.shared.dto.pagination.PaginationRequestDTO;
import com.jardvcode.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "checklists/templates")
public final class TemplateController {

    private final TemplateCrud crud;

    public TemplateController(TemplateCrud crud) {
        this.crud = crud;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<TemplateDTO>> create(@RequestBody TemplateDTO dto) {
        return ResponseWrapper.ok(TemplateDTO.fromEntity(crud.create(dto)));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<TemplateDTO>> update(@RequestBody TemplateDTO dto) {
        return ResponseWrapper.ok(TemplateDTO.fromEntity(crud.update(dto)));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponseWrapper<Void>> delete(@PathVariable Long id) {
        crud.deleteById(id);
        return ResponseWrapper.ok(null);
    }

    @GetMapping(value = "/structure")
    public ResponseEntity<ResponseWrapper<TemplateStructureResponseDTO>> fetchStructure(@RequestParam("id") Long id) {
        return ResponseWrapper.ok(TemplateStructureResponseDTO.fromEntity(crud.findWithSectionsAndItemsById(id)));
    }

    @PutMapping(value = "/structure")
    public ResponseEntity<ResponseWrapper<Void>> updateStructure(@RequestBody TemplateStructureRequestDTO dto) {
        crud.updateStructure(dto);
        return ResponseWrapper.ok(null);
    }

    @GetMapping(value = "/catalog")
    public ResponseEntity<ResponseWrapper<List<TemplateCatalogDTO>>> findAll() {
        return ResponseWrapper.ok(TemplateCatalogDTO.fromEntities(crud.findAll()));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<TemplateDTO>>> search(@ModelAttribute PaginationRequestDTO dto) {
        return ResponseWrapper.ok(ResponsePaginationDTO.create(crud.search(dto), TemplateDTO::fromEntity));
    }

}
