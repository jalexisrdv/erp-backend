package com.erp.authentication.dto;

public record ResetPasswordTokenDTO(Long userId, String token) {
}
