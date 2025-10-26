package com.erp.user.controller;

import com.erp.shared.domain.ResponseWrapper;
import com.erp.shared.dto.pagination.PaginationRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import com.erp.user.dto.RoleDTO;
import com.erp.user.dto.UserDTO;
import com.erp.user.mapper.RoleMapper;
import com.erp.user.mapper.UserMapper;
import com.erp.user.service.UserCrud;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "users")
public final class UserController {

    private final UserCrud crud;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    public UserController(UserCrud crud) {
        this.crud = crud;
        this.userMapper = new UserMapper();
        this.roleMapper = new RoleMapper();
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<UserDTO>> create(@RequestBody UserDTO dto) {
        return ResponseWrapper.ok(userMapper.fromEntity(crud.create(userMapper.fromDTO(dto))));
    }

    @PostMapping(value = "/pagination")
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<UserDTO>>> searchByPage(@RequestBody PaginationRequestDTO dto) {
        return ResponseWrapper.ok(userMapper.fromPagination(crud.searchByPage(dto)));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<UserDTO>> update(@RequestBody UserDTO dto) {
        return ResponseWrapper.ok(userMapper.fromEntity(crud.update(userMapper.fromDTO(dto))));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteById(@PathVariable Long id) {
        crud.deleteById(id);
        return ResponseWrapper.ok(null);
    }

    @PutMapping(value = "{id}/roles")
    public ResponseEntity<ResponseWrapper<List<RoleDTO>>> assignRoles(@PathVariable Long id, @RequestBody List<RoleDTO> dtos) {
        return ResponseWrapper.ok(roleMapper.fromEntity(crud.assignRoles(id, roleMapper.fromDTO(dtos))));
    }

    @GetMapping(value = "{id}/roles")
    public ResponseEntity<ResponseWrapper<List<RoleDTO>>> fetchRoles(@PathVariable Long id) {
        return ResponseWrapper.ok(roleMapper.fromEntity(crud.fetchRoles(id)));
    }

}
