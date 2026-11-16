package com.jardvcode.erp.inventory.dto;

import com.jardvcode.erp.inventory.exception.movement.CorruptedInvoiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public record EntryMovementRequestDTO(
        Long id,
        Long itemId,
        Long quantity,
        MultipartFile document,
        String rejectReason
) {
    private final static Logger LOG = LoggerFactory.getLogger(EntryMovementRequestDTO.class);

    @Override
    public MultipartFile document() {
        throw new UnsupportedOperationException("Usa documentBytes() para interactuar con el archivo.");
    }

    public byte[] documentBytes() {
        if (document == null) {
            return null;
        }

        try {
            return document.getBytes();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new CorruptedInvoiceException();
        }
    }

    public String documentName() {
        return document != null ? document.getOriginalFilename() : null;
    }

}
