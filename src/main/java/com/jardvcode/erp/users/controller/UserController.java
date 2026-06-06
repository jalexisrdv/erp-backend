package com.jardvcode.erp.users.controller;

import com.jardvcode.erp.shared.domain.ResponseWrapper;
import com.jardvcode.erp.shared.dto.pagination.PaginationRequestDTO;
import com.jardvcode.erp.shared.dto.pagination.ResponsePaginationDTO;
import com.jardvcode.erp.users.dto.RoleDTO;
import com.jardvcode.erp.users.dto.UserCatalogDTO;
import com.jardvcode.erp.users.dto.UserDTO;
import com.jardvcode.erp.users.service.UserCrud;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "users")
public final class UserController {

    private final UserCrud crud;

    public UserController(UserCrud crud) {
        this.crud = crud;
    }

    @GetMapping(value = "/catalog")
    public ResponseEntity<ResponseWrapper<List<UserCatalogDTO>>> findAll() {
        return ResponseWrapper.ok(UserCatalogDTO.fromEntities(crud.findAll()));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<UserDTO>>> search(@ModelAttribute PaginationRequestDTO dto) {
        return ResponseWrapper.ok(ResponsePaginationDTO.create(crud.search(dto), UserDTO::fromEntity));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<UserDTO>> create(@RequestBody UserDTO dto) {
        return ResponseWrapper.ok(UserDTO.fromEntity(crud.create(dto)));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<UserDTO>> update(@RequestBody UserDTO dto) {
        return ResponseWrapper.ok(UserDTO.fromEntity(crud.update(dto)));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteById(@PathVariable Long id) {
        crud.deleteById(id);
        return ResponseWrapper.ok(null);
    }

    @PutMapping(value = "{id}/roles")
    public ResponseEntity<ResponseWrapper<List<RoleDTO>>> assignRoles(@PathVariable Long id, @RequestBody List<RoleDTO> dtos) {
        return ResponseWrapper.ok(RoleDTO.fromEntities(crud.assignRoles(id, dtos)));
    }

    @GetMapping(value = "{id}/roles")
    public ResponseEntity<ResponseWrapper<List<RoleDTO>>> fetchRoles(@PathVariable Long id) {
        return ResponseWrapper.ok(RoleDTO.fromEntities(crud.fetchRoles(id)));
    }

}
