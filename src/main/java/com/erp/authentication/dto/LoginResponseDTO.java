package com.erp.authentication.dto;

public record LoginResponseDTO(
        String accessToken,
        String refreshToken
) {
}
