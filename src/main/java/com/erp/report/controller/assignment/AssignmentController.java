package com.erp.report.controller.assignment;

import com.erp.report.dto.assignment.AssignmentDTO;
import com.erp.report.mapper.assignment.AssignmentMapper;
import com.erp.report.service.assignment.AssigmentCrud;
import com.erp.shared.domain.ResponseWrapper;
import com.erp.shared.dto.pagination.PaginationRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "reports/assignments")
public final class AssignmentController {

    private final AssigmentCrud crud;
    private final AssignmentMapper mapper;

    public AssignmentController(AssigmentCrud crud) {
        this.crud = crud;
        this.mapper = new AssignmentMapper();
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<AssignmentDTO>> create(@RequestBody AssignmentDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.create(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/pagination")
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<AssignmentDTO>>> searchByPage(@RequestBody PaginationRequestDTO dto) {
        return ResponseWrapper.ok(mapper.fromPagination(crud.searchByPage(dto)));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<AssignmentDTO>> update(@RequestBody AssignmentDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.update(mapper.fromDTO(dto))));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponseWrapper<Void>> delete(@PathVariable Long id) {
        crud.deleteById(id);
        return ResponseWrapper.ok(null);
    }

}
