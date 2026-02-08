package com.erp.report.controller.template;

import com.erp.report.dto.template.SectionDTO;
import com.erp.report.mapper.template.SectionMapper;
import com.erp.report.service.template.SectionCrud;
import com.erp.shared.domain.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"reports/templates/{templateId}/sections"})
public final class TemplateSectionController {

    private final SectionCrud crud;
    private final SectionMapper mapper;

    public TemplateSectionController(SectionCrud crud) {
        this.crud = crud;
        this.mapper = new SectionMapper();
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<SectionDTO>>> findByTemplateId(@PathVariable Long templateId) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.findByTemplateId(templateId)));
    }

}
