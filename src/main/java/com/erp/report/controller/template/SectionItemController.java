package com.erp.report.controller.template;

import com.erp.report.dto.template.ItemDTO;
import com.erp.report.mapper.template.ItemMapper;
import com.erp.report.service.template.ItemCrud;
import com.erp.shared.domain.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "reports/templates/sections/{sectionId}/items")
public final class SectionItemController {

    private final ItemCrud crud;
    private final ItemMapper mapper;

    public SectionItemController(ItemCrud crud) {
        this.crud = crud;
        this.mapper = new ItemMapper();
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ItemDTO>>> findBySectionId(@PathVariable Long sectionId) {
        return ResponseWrapper.ok(mapper.fromEntity(crud.findBySectionId(sectionId)));
    }

}
