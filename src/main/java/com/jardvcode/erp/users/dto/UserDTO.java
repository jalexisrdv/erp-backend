package com.jardvcode.erp.users.dto;

import com.jardvcode.erp.users.entity.UserEntity;

public record UserDTO(
		Long id,
		String firstName,
		String middleName,
		String lastName,
		String secondLastName,
		String phoneNumber,
		String username
) {

	public static UserDTO fromEntity(UserEntity entity) {
		return new UserDTO(
				entity.getId(),
				entity.getFirstName(),
				entity.getMiddleName(),
				entity.getLastName(),
				entity.getSecondLastName(),
				entity.getPhoneNumber(),
				entity.getUsername()
		);
	}

}