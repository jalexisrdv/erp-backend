package com.erp.user.dto;

public record UserDTO(
		Long id,
		String firstName,
		String middleName,
		String lastName,
		String secondLastName,
		String phoneNumber,
		String username
) {}