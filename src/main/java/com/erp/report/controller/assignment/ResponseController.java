package com.erp.report.controller.assignment;

import com.erp.report.dto.response.ResponseRequestDTO;
import com.erp.report.dto.response.ResponsesSavedDTO;
import com.erp.report.mapper.assignment.ResponseMapper;
import com.erp.report.service.assignment.ResponseCrud;
import com.erp.shared.domain.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "reports/assignments/responses")
public final class ResponseController {

    private final ResponseCrud crud;
    private final ResponseMapper mapper;

    public ResponseController(ResponseCrud crud) {
        this.crud = crud;
        this.mapper = new ResponseMapper();
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper<ResponsesSavedDTO>> update(@RequestBody List<ResponseRequestDTO> dtos) {
        return ResponseWrapper.ok(mapper.fromEntities(crud.update(mapper.fromDTO(dtos))));
    }

}
