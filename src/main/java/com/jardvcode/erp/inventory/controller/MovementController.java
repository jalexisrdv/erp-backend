package com.jardvcode.erp.inventory.controller;

import com.jardvcode.erp.inventory.dto.*;
import com.jardvcode.erp.inventory.service.movement.EntryMovementService;
import com.jardvcode.erp.inventory.service.movement.MovementSearcher;
import com.jardvcode.erp.inventory.service.movement.OutputMovementService;
import com.jardvcode.erp.shared.domain.ResponseWrapper;
import com.jardvcode.erp.shared.dto.pagination.PaginationRequestDTO;
import com.jardvcode.erp.shared.dto.pagination.ResponsePaginationDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "inventory/movements")
public final class MovementController {

    private final EntryMovementService entryMovementService;
    private final OutputMovementService outputMovementService;
    private final MovementSearcher searcher;

    public MovementController(EntryMovementService entryMovementService, OutputMovementService outputMovementService, MovementSearcher searcher) {
        this.entryMovementService = entryMovementService;
        this.outputMovementService = outputMovementService;
        this.searcher = searcher;
    }

    @GetMapping(value = "{id}/invoice/preview")
    public ResponseEntity<ResponseWrapper<InvoicePreviewDTO>> previewInvoice(@PathVariable  Long id) {
        return ResponseWrapper.ok(InvoicePreviewDTO.fromUrl(entryMovementService.previewInvoice(id)));
    }

    @PostMapping(value = "/entries", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseWrapper<MovementDTO>> createEntry(@ModelAttribute EntryMovementRequestDTO dto) {
        return ResponseWrapper.ok(MovementDTO.fromEntity(entryMovementService.create(dto)));
    }

    @PutMapping(value = "/entries", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseWrapper<MovementDTO>> updateEntry(@ModelAttribute EntryMovementRequestDTO dto) {
        return ResponseWrapper.ok(MovementDTO.fromEntity(entryMovementService.update(dto)));
    }

    @PostMapping(value = "/entries/approve")
    public ResponseEntity<ResponseWrapper<MovementDTO>> approveEntry(@RequestBody ApproveMovementRequestDTO dto) {
        return ResponseWrapper.ok(MovementDTO.fromEntity(entryMovementService.approve(dto)));
    }

    @PostMapping(value = "/entries/reject")
    public ResponseEntity<ResponseWrapper<MovementDTO>> rejectEntry(@RequestBody RejectMovementRequestDTO dto) {
        return ResponseWrapper.ok(MovementDTO.fromEntity(entryMovementService.reject(dto)));
    }

    @PostMapping(value = "/outputs")
    public ResponseEntity<ResponseWrapper<MovementDTO>> createOutput(@RequestBody OutputMovementRequestDTO dto) {
        return ResponseWrapper.ok(MovementDTO.fromEntity(outputMovementService.create(dto)));
    }

    @PutMapping(value = "/outputs")
    public ResponseEntity<ResponseWrapper<MovementDTO>> updateOutput(@RequestBody OutputMovementRequestDTO dto) {
        return ResponseWrapper.ok(MovementDTO.fromEntity(outputMovementService.update(dto)));
    }

    @PostMapping(value = "/outputs/approve")
    public ResponseEntity<ResponseWrapper<MovementDTO>> approveOutput(@RequestBody ApproveMovementRequestDTO dto) {
        return ResponseWrapper.ok(MovementDTO.fromEntity(outputMovementService.approve(dto)));
    }

    @PostMapping(value = "/outputs/reject")
    public ResponseEntity<ResponseWrapper<MovementDTO>> rejectOutput(@RequestBody RejectMovementRequestDTO dto) {
        return ResponseWrapper.ok(MovementDTO.fromEntity(outputMovementService.reject(dto)));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<ResponsePaginationDTO<MovementDTO>>> search(@ModelAttribute PaginationRequestDTO paginationRequestDTO, @ModelAttribute MovementFilterDTO filterDTO) {
        return ResponseWrapper.ok(ResponsePaginationDTO.create(searcher.search(paginationRequestDTO, filterDTO), MovementDTO::fromEntity));
    }

}
