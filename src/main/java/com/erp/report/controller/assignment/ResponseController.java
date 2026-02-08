package com.erp.report.controller.assignment;

import com.erp.report.dto.response.ResponseDTO;
import com.erp.report.dto.response.detail.ReportDTO;
import com.erp.report.mapper.assignment.ReportMapper;
import com.erp.report.mapper.assignment.ResponseMapper;
import com.erp.report.service.assignment.ResponseCrud;
import com.erp.shared.domain.ResponseWrapper;
import com.erp.shared.dto.pagination.PaginationRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "reports/assignments/responses")
public final class ResponseController {

    private final ResponseCrud crud;
    private final ResponseMapper mapper;
    private final ReportMapper reportMapper;

    public ResponseController(ResponseCrud crud) {
        this.crud = crud;
        this.mapper = new ResponseMapper();
        this.reportMapper = new ReportMapper();
    }

    @GetMapping(value = "detail")
    public ResponseEntity<ResponseWrapper<ReportDTO>> create(@RequestParam(value = "templateId") Long templateId) {
        return ResponseWrapper.ok(reportMapper.fromEntity(crud.findByTemplateId(templateId)));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ResponseDTO>> create(@RequestBody ResponseDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.create(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/pagination")
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<ResponseDTO>>> searchByPage(@RequestBody PaginationRequestDTO dto) {
        return ResponseWrapper.ok(mapper.fromPagination(crud.searchByPage(dto)));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<ResponseDTO>> update(@RequestBody ResponseDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.update(mapper.fromDTO(dto))));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponseWrapper<Void>> delete(@PathVariable Long id) {
        crud.deleteById(id);
        return ResponseWrapper.ok(null);
    }

}
