package com.jardvcode.erp.checklists.controller.assignment;

import com.jardvcode.erp.checklists.dto.assignment.AssignmentDTO;
import com.jardvcode.erp.checklists.dto.response.ResponseRequestDTO;
import com.jardvcode.erp.checklists.dto.response.detail.ReportDTO;
import com.jardvcode.erp.checklists.service.assignment.AssigmentCrud;
import com.jardvcode.erp.shared.domain.ResponseWrapper;
import com.jardvcode.erp.shared.dto.pagination.PaginationRequestDTO;
import com.jardvcode.erp.shared.dto.pagination.ResponsePaginationDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "reports/assignments")
public final class AssignmentController {

    private final AssigmentCrud crud;

    public AssignmentController(AssigmentCrud crud) {
        this.crud = crud;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<AssignmentDTO>> create(@RequestBody AssignmentDTO dto) {
        return ResponseWrapper.ok(AssignmentDTO.fromEntity(crud.create(dto)));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<AssignmentDTO>>> search(@Valid @RequestParam PaginationRequestDTO dto) {
        return ResponseWrapper.ok(ResponsePaginationDTO.create(crud.search(dto), AssignmentDTO::fromEntity));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<AssignmentDTO>> update(@RequestBody AssignmentDTO dto) {
        return ResponseWrapper.ok(AssignmentDTO.fromEntity(crud.update(dto)));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponseWrapper<Void>> delete(@PathVariable Long id) {
        crud.deleteById(id);
        return ResponseWrapper.ok(null);
    }

    @GetMapping(value = "{id}/responses")
    public ResponseEntity<ResponseWrapper<ReportDTO>> fetchResponses(@PathVariable Long id) {
        return ResponseWrapper.ok(ReportDTO.fromEntity(crud.findWithTemplateAndResponsesById(id)));
    }

    @PutMapping(value = "{id}/responses")
    public ResponseEntity<ResponseWrapper<Void>> updateResponses(@PathVariable Long assignmentId, @RequestBody List<ResponseRequestDTO> dtos) {
        crud.updateResponses(assignmentId, dtos);

        return ResponseWrapper.ok(null);
    }

}
