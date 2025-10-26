package com.erp.permission.controller;

import com.erp.permission.dto.PermissionDTO;
import com.erp.permission.mapper.PermissionMapper;
import com.erp.permission.service.PermissionCrud;
import com.erp.shared.domain.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "permissions")
public final class PermissionController {

    private final PermissionCrud crud;
    private final PermissionMapper mapper;

    public PermissionController(PermissionCrud crud, PermissionMapper mapper) {
        this.crud = crud;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<PermissionDTO>> create(@RequestBody PermissionDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.create(mapper.fromDTO(dto))));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<PermissionDTO>>> findAll() {
        return ResponseWrapper.ok(mapper.fromEntity(crud.findAll()));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<PermissionDTO>> update(@RequestBody PermissionDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.update(mapper.fromDTO(dto))));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteById(@PathVariable Long id) {
        crud.deleteById(id);
        return ResponseWrapper.ok(null);
    }

}
