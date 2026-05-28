package com.jardvcode.erp.authentication.dto;

public record ChangePasswordDTO(String username, String currentPassword, String newPassword) {
}
