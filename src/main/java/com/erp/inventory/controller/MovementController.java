package com.erp.inventory.controller;

import com.erp.authentication.service.AuthenticatedUserProvider;
import com.erp.inventory.dto.*;
import com.erp.inventory.mapper.MovementMapper;
import com.erp.inventory.service.movement.EntryMovementService;
import com.erp.inventory.service.movement.MovementSearcher;
import com.erp.inventory.service.movement.OutputMovementService;
import com.erp.shared.domain.ResponseWrapper;
import com.erp.shared.dto.pagination.PaginatedSearchRequestDTO;
import com.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "inventory/movements")
public final class MovementController {

    private final EntryMovementService entryMovementService;
    private final OutputMovementService outputMovementService;
    private final MovementSearcher searcher;
    private final MovementMapper mapper;
    private final AuthenticatedUserProvider userProvider;

    public MovementController(EntryMovementService entryMovementService, OutputMovementService outputMovementService, MovementSearcher searcher, AuthenticatedUserProvider userProvider) {
        this.entryMovementService = entryMovementService;
        this.outputMovementService = outputMovementService;
        this.searcher = searcher;
        this.userProvider = userProvider;
        this.mapper = new MovementMapper();
    }

    @GetMapping(value = "{id}/invoice/preview")
    public ResponseEntity<ResponseWrapper<InvoicePreviewDTO>> previewInvoice(@PathVariable  Long id) {
        return ResponseWrapper.ok(mapper.toDTO(entryMovementService.previewInvoice(id)));
    }

    @PostMapping(value = "/entries", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseWrapper<MovementDTO>> createEntry(@ModelAttribute EntryMovementRequestDTO dto) {
        return ResponseWrapper.ok(mapper.toDTO(entryMovementService.create(dto, userProvider.getUserId())));
    }

    @PutMapping(value = "/entries", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseWrapper<MovementDTO>> updateEntry(@ModelAttribute EntryMovementRequestDTO dto) {
        return ResponseWrapper.ok(mapper.toDTO(entryMovementService.update(dto, userProvider.getUserId())));
    }

    @PostMapping(value = "/entries/approve")
    public ResponseEntity<ResponseWrapper<MovementDTO>> approveEntry(@RequestBody ApproveMovementRequestDTO dto) {
        return ResponseWrapper.ok(mapper.toDTO(entryMovementService.approve(mapper.toEntity(dto, userProvider.getUserId()))));
    }

    @PostMapping(value = "/entries/reject")
    public ResponseEntity<ResponseWrapper<MovementDTO>> rejectEntry(@RequestBody RejectMovementRequestDTO dto) {
        return ResponseWrapper.ok(mapper.toDTO(entryMovementService.reject(mapper.toEntity(dto, userProvider.getUserId()))));
    }

    @PostMapping(value = "/outputs")
    public ResponseEntity<ResponseWrapper<MovementDTO>> createOutput(@RequestBody OutputMovementRequestDTO dto) {
        return ResponseWrapper.ok(mapper.toDTO(outputMovementService.create(dto, userProvider.getUserId())));
    }

    @PutMapping(value = "/outputs")
    public ResponseEntity<ResponseWrapper<MovementDTO>> updateOutput(@RequestBody OutputMovementRequestDTO dto) {
        return ResponseWrapper.ok(mapper.toDTO(outputMovementService.update(dto, userProvider.getUserId())));
    }

    @PostMapping(value = "/outputs/approve")
    public ResponseEntity<ResponseWrapper<MovementDTO>> approveOutput(@RequestBody ApproveMovementRequestDTO dto) {
        return ResponseWrapper.ok(mapper.toDTO(outputMovementService.approve(mapper.toEntity(dto, userProvider.getUserId()))));
    }

    @PostMapping(value = "/outputs/reject")
    public ResponseEntity<ResponseWrapper<MovementDTO>> rejectOutput(@RequestBody RejectMovementRequestDTO dto) {
        return ResponseWrapper.ok(mapper.toDTO(outputMovementService.reject(mapper.toEntity(dto, userProvider.getUserId()))));
    }

    @PostMapping(value = "/pagination")
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<MovementDTO>>> searchByPage(@RequestBody PaginatedSearchRequestDTO<MovementFilterDTO> dto) {
        return ResponseWrapper.ok(mapper.fromPagination(searcher.searchByPage(dto)));
    }

}
