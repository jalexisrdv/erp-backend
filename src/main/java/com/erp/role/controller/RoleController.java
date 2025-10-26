package com.erp.role.controller;

import com.erp.role.dto.PermissionDTO;
import com.erp.role.dto.RoleDTO;
import com.erp.role.mapper.PermissionMapper;
import com.erp.role.mapper.RoleMapper;
import com.erp.role.service.RoleCrud;
import com.erp.shared.dto.pagination.PaginationRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import com.erp.shared.domain.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "roles")
public final class RoleController {

    private final RoleCrud crud;
    private final RoleMapper roleMapper;
    private final PermissionMapper permissionMapper;

    public RoleController(RoleCrud crud) {
        this.crud = crud;
        this.roleMapper = new RoleMapper();
        this.permissionMapper = new PermissionMapper();
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<RoleDTO>> create(@RequestBody RoleDTO dto) {
        return ResponseWrapper.ok(roleMapper.fromEntity(crud.create(roleMapper.fromDTO(dto))));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<RoleDTO>>> findAll() {
        return ResponseWrapper.ok(roleMapper.fromEntity(crud.findAll()));
    }

    @PostMapping(value = "/pagination")
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<RoleDTO>>> fetchByPagination(@RequestBody PaginationRequestDTO dto) {
        return ResponseWrapper.ok(roleMapper.fromPagination(crud.searchByPagination(dto)));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<RoleDTO>> update(@RequestBody RoleDTO dto) {
        return ResponseWrapper.ok(roleMapper.fromEntity(crud.update(roleMapper.fromDTO(dto))));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteById(@PathVariable Long id) {
        crud.deleteById(id);
        return ResponseWrapper.ok(null);
    }

    @PutMapping(value = "{id}/permissions")
    public ResponseEntity<ResponseWrapper<List<PermissionDTO>>> assignPermissions(@PathVariable Long id, @RequestBody List<PermissionDTO> permissions) {
        return ResponseWrapper.ok(permissionMapper.fromEntity(crud.assignPermissions(id, permissionMapper.fromDTO(permissions))));
    }

    @GetMapping(value = "{id}/permissions")
    public ResponseEntity<ResponseWrapper<List<PermissionDTO>>> fetchPermissionsByPage(@PathVariable Long id) {
        return ResponseWrapper.ok(permissionMapper.fromEntity(crud.fetchPermissions(id)));
    }

}
