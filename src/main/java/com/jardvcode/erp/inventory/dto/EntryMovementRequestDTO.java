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
        MultipartFile invoice,
        String rejectReason
) {
    private final static Logger LOG = LoggerFactory.getLogger(EntryMovementRequestDTO.class);

    @Override
    public MultipartFile invoice() {
        throw new UnsupportedOperationException("Usa invoiceBytes() para interactuar con el archivo.");
    }

    public byte[] invoiceBytes() {
        if (invoice == null) {
            return null;
        }

        try {
            return invoice.getBytes();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new CorruptedInvoiceException();
        }
    }

    public String invoiceName() {
        return invoice != null ? invoice.getOriginalFilename() : null;
    }

}
