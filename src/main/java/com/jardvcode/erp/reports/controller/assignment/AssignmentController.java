package com.jardvcode.erp.reports.controller.assignment;

import com.jardvcode.erp.reports.dto.assignment.AssignmentDTO;
import com.jardvcode.erp.reports.dto.response.AssignmentStatusDTO;
import com.jardvcode.erp.reports.dto.response.ResponseRequestDTO;
import com.jardvcode.erp.reports.dto.response.detail.ReportDTO;
import com.jardvcode.erp.reports.mapper.assignment.AssignmentMapper;
import com.jardvcode.erp.reports.mapper.assignment.ReportResponseMapper;
import com.jardvcode.erp.reports.service.assignment.AssigmentCrud;
import com.jardvcode.erp.shared.domain.ResponseWrapper;
import com.jardvcode.erp.shared.dto.pagination.PaginationRequestDTO;
import com.jardvcode.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "reports/assignments")
public final class AssignmentController {

    private final AssigmentCrud crud;
    private final AssignmentMapper mapper;
    private final ReportResponseMapper reportMapper;

    public AssignmentController(AssigmentCrud crud) {
        this.crud = crud;
        this.mapper = new AssignmentMapper();
        this.reportMapper = new ReportResponseMapper();
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

    @GetMapping(value = "{id}/responses")
    public ResponseEntity<ResponseWrapper<ReportDTO>> fetchResponses(@PathVariable Long id) {
        return ResponseWrapper.ok(reportMapper.fromEntity(crud.findWithTemplateAndResponsesById(id)));
    }

    @PutMapping(value = "{id}/responses")
    public ResponseEntity<ResponseWrapper<AssignmentStatusDTO>> updateResponses(@PathVariable Long id, @RequestBody List<ResponseRequestDTO> dtos) {
        return ResponseWrapper.ok(mapper.toDTO(crud.updateResponses(mapper.toEntity(id, dtos))));
    }

}
