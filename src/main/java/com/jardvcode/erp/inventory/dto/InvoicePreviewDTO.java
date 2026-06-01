package com.jardvcode.erp.inventory.dto;

public record InvoicePreviewDTO(
        String url
) {

    public static InvoicePreviewDTO fromUrl(String url) {
        return new InvoicePreviewDTO(url);
    }

}
