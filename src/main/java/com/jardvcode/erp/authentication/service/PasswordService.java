package com.jardvcode.erp.authentication.service;

import com.jardvcode.erp.authentication.dto.ChangePasswordDTO;
import com.jardvcode.erp.authentication.dto.ResetPasswordDTO;
import com.jardvcode.erp.authentication.exception.InvalidCurrentCredentialsException;
import com.jardvcode.erp.shared.domain.DomainErrorType;
import com.jardvcode.erp.users.entity.UserEntity;
import com.jardvcode.erp.users.exception.UserDoesNotExistException;
import com.jardvcode.erp.users.repository.UserRepository;
import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.authentication.util.BCryptPasswordEncoderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
	
	private final static Logger LOG = LoggerFactory.getLogger(PasswordService.class);
	
	private final UserRepository repository;

	public PasswordService(UserRepository repository) {
		this.repository = repository;
    }

	public ResetPasswordDTO reset(ResetPasswordDTO dto) {
		try {
			UserEntity entity = repository.findById(dto.userId()).orElseThrow(() -> new UserDoesNotExistException(DomainErrorType.DEPENDENCY));

			String password = PasswordTokenGenerator.generate();

			entity.resetPassword(BCryptPasswordEncoderUtil.hashPassword(password));

			repository.save(entity);

			return new ResetPasswordDTO(entity.getId(), password);
		} catch (DomainError e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}
	
	public void change(ChangePasswordDTO dto) {
		try {
			UserEntity entity = repository.findByUsername(dto.username()).orElseThrow(() -> new InvalidCurrentCredentialsException());

			boolean isValidPassword = BCryptPasswordEncoderUtil.checkPassword(dto.currentPassword(), entity.getPassword());

			if(!isValidPassword) {
				throw new InvalidCurrentCredentialsException();
			}

			entity.changePassword(BCryptPasswordEncoderUtil.hashPassword(dto.newPassword()));

			repository.save(entity);
		} catch (DomainError e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

}
