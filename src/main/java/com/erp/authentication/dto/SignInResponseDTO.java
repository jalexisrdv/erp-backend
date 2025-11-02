package com.erp.authentication.dto;

import java.util.Set;

public record SignInResponseDTO(
        String username,
        Set<String> roles,
        Set<String> permissions,
        String token,
        boolean resetPassword
) {
}
