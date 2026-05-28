package com.jardvcode.erp.authorization.controller;

import com.jardvcode.erp.authorization.dto.permission.PermissionRequestDTO;
import com.jardvcode.erp.authorization.dto.permission.PermissionResponseDTO;
import com.jardvcode.erp.authorization.mapper.permission.PermissionMapper;
import com.jardvcode.erp.authorization.service.PermissionCrud;
import com.jardvcode.erp.shared.domain.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "permissions")
public final class PermissionController {

    private final PermissionCrud crud;
    private final PermissionMapper mapper;

    public PermissionController(PermissionCrud crud) {
        this.crud = crud;
        this.mapper = new PermissionMapper();
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<PermissionResponseDTO>> create(@RequestBody PermissionRequestDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.create(mapper.fromDTO(dto))));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<PermissionResponseDTO>>> findByModuleId(@RequestParam("moduleId") Long moduleId) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.findByModuleId(moduleId)));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<PermissionResponseDTO>> update(@RequestBody PermissionRequestDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.update(mapper.fromDTO(dto))));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteById(@PathVariable Long id) {
        crud.deleteById(id);
        return ResponseWrapper.ok(null);
    }

}
