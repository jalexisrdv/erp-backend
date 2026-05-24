package com.erp.authorization.controller;

import com.erp.authorization.dto.role.PermissionDTO;
import com.erp.authorization.dto.role.RoleDTO;
import com.erp.authorization.mapper.role.PermissionMapper;
import com.erp.authorization.mapper.role.RoleMapper;
import com.erp.authorization.service.RoleCrud;
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
    public ResponseEntity<ResponseWrapper<List<PermissionDTO>>> findPermissionsByRoleId(@PathVariable Long id) {
        return ResponseWrapper.ok(permissionMapper.fromEntity(crud.findPermissionsByRoleId(id)));
    }

}
