package com.erp.inventory.controller;

import com.erp.inventory.dto.MovementDTO;
import com.erp.inventory.dto.MovementFilterDTO;
import com.erp.inventory.mapper.MovementMapper;
import com.erp.inventory.service.movement.EntryMovementService;
import com.erp.inventory.service.movement.MovementSearcher;
import com.erp.inventory.service.movement.OutputMovementService;
import com.erp.shared.domain.ResponseWrapper;
import com.erp.shared.dto.pagination.PaginatedSearchRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "inventory/movements")
public final class MovementController {

    private final EntryMovementService entryMovementService;
    private final OutputMovementService outputMovementService;
    private final MovementSearcher searcher;
    private final MovementMapper mapper;

    public MovementController(EntryMovementService entryMovementService, OutputMovementService outputMovementService, MovementSearcher searcher) {
        this.entryMovementService = entryMovementService;
        this.outputMovementService = outputMovementService;
        this.searcher = searcher;
        this.mapper = new MovementMapper();
    }

    @PostMapping(value = "/entries")
    public ResponseEntity<ResponseWrapper<MovementDTO>> createEntry(@RequestBody MovementDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(entryMovementService.create(mapper.fromDTO(dto))));
    }

    @PutMapping(value = "/entries")
    public ResponseEntity<ResponseWrapper<MovementDTO>> updateEntry(@RequestBody MovementDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(entryMovementService.update(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/entries/approve")
    public ResponseEntity<ResponseWrapper<MovementDTO>> approveEntry(@RequestBody MovementDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(entryMovementService.approve(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/entries/reject")
    public ResponseEntity<ResponseWrapper<MovementDTO>> rejectEntry(@RequestBody MovementDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(entryMovementService.reject(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/outputs")
    public ResponseEntity<ResponseWrapper<MovementDTO>> createOutput(@RequestBody MovementDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(outputMovementService.create(mapper.fromDTO(dto))));
    }

    @PutMapping(value = "/outputs")
    public ResponseEntity<ResponseWrapper<MovementDTO>> updateOutput(@RequestBody MovementDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(outputMovementService.update(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/outputs/approve")
    public ResponseEntity<ResponseWrapper<MovementDTO>> approveOutput(@RequestBody MovementDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(outputMovementService.approve(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/outputs/reject")
    public ResponseEntity<ResponseWrapper<MovementDTO>> rejectOutput(@RequestBody MovementDTO dto) {
        return ResponseWrapper.ok(mapper.fromEntity(outputMovementService.reject(mapper.fromDTO(dto))));
    }

    @PostMapping(value = "/pagination")
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<MovementDTO>>> searchByPage(@RequestBody PaginatedSearchRequestDTO<MovementFilterDTO> dto) {
        return ResponseWrapper.ok(mapper.fromPagination(searcher.searchByPage(dto)));
    }

}
