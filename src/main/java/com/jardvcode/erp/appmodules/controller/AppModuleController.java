package com.jardvcode.erp.appmodules.controller;

import com.jardvcode.erp.appmodules.dto.ModuleRequestDTO;
import com.jardvcode.erp.appmodules.dto.ModuleResponseDTO;
import com.jardvcode.erp.appmodules.mapper.AppModuleMapper;
import com.jardvcode.erp.appmodules.service.AppModuleCrud;
import com.jardvcode.erp.shared.domain.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "app-modules")
public final class AppModuleController {

    private final AppModuleCrud crud;
    private final AppModuleMapper mapper;
    
    public AppModuleController(AppModuleCrud crud) {
        this.crud = crud;
        this.mapper = new AppModuleMapper();
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ModuleResponseDTO>> create(@RequestBody ModuleRequestDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.create(mapper.fromDTO(dto))));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ModuleResponseDTO>>> findByParentIdNotNull() {
        return ResponseWrapper.ok(mapper.fromEntity(crud.findByParentIdNotNull()));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<ModuleResponseDTO>> update(@RequestBody ModuleRequestDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.update(mapper.fromDTO(dto))));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteById(@PathVariable Long id) {
        crud.deleteById(id);
        return ResponseWrapper.ok(null);
    }

}
