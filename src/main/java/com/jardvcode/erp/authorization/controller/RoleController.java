package com.jardvcode.erp.authorization.controller;

import com.jardvcode.erp.authorization.dto.role.PermissionDTO;
import com.jardvcode.erp.authorization.dto.role.RoleDTO;
import com.jardvcode.erp.authorization.service.RoleCrud;
import com.jardvcode.erp.shared.dto.pagination.PaginationRequestDTO;
import com.jardvcode.erp.shared.dto.pagination.ResponsePaginationDTO;
import com.jardvcode.erp.shared.domain.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "roles")
public final class RoleController {

    private final RoleCrud crud;

    public RoleController(RoleCrud crud) {
        this.crud = crud;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<RoleDTO>> create(@RequestBody RoleDTO dto) {
        return ResponseWrapper.ok(RoleDTO.fromEntity(crud.create(dto)));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<RoleDTO>> update(@RequestBody RoleDTO dto) {
        return ResponseWrapper.ok(RoleDTO.fromEntity(crud.update(dto)));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteById(@PathVariable Long id) {
        crud.deleteById(id);
        return ResponseWrapper.ok(null);
    }

    @PutMapping(value = "{id}/permissions")
    public ResponseEntity<ResponseWrapper<List<PermissionDTO>>> assignPermissions(@PathVariable Long id, @RequestBody List<PermissionDTO> permissions) {
        return ResponseWrapper.ok(PermissionDTO.fromEntities(crud.assignPermissions(id, permissions)));
    }

    @GetMapping(value = "{id}/permissions")
    public ResponseEntity<ResponseWrapper<List<PermissionDTO>>> findPermissionsByRoleId(@PathVariable Long id) {
        return ResponseWrapper.ok(PermissionDTO.fromEntities(crud.findPermissionsByRoleId(id)));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<ResponseWrapper<List<RoleDTO>>> findAll() {
        return ResponseWrapper.ok(RoleDTO.fromEntities(crud.findAll()));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<RoleDTO>>> search(@ModelAttribute PaginationRequestDTO dto) {
        return ResponseWrapper.ok(ResponsePaginationDTO.create(crud.search(dto), RoleDTO::fromEntity));
    }

}
