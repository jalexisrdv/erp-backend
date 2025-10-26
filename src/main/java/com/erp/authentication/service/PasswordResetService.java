package com.erp.authentication.service;

import java.time.LocalDateTime;

import com.erp.authentication.dto.ResetPasswordDTO;
import com.erp.authentication.dto.ResetPasswordTokenDTO;
import com.erp.shared.domain.HttpCode;
import com.erp.user.entity.UserEntity;
import com.erp.authentication.exception.InvalidPasswordResetTokenException;
import com.erp.authentication.exception.UserDoesNotExistException;
import com.erp.authentication.repository.UserRepository;
import com.erp.shared.domain.DomainError;
import com.erp.authentication.util.BCryptPasswordEncoderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {
	
	private final static Logger LOG = LoggerFactory.getLogger(PasswordResetService.class);
	
	private final UserRepository repository;
	
	public PasswordResetService(UserRepository repository) {
		this.repository = repository;
	}

	public ResetPasswordTokenDTO generateToken(ResetPasswordTokenDTO dto) {
		try {
			UserEntity entity = repository.findById(dto.userId()).orElseThrow(() -> new UserDoesNotExistException(HttpCode.conflict()));

			entity.setToken(PasswordTokenGenerator.generate());
			entity.setTokenExpirationDate(LocalDateTime.now().plusDays(1));

			repository.save(entity);

			return new ResetPasswordTokenDTO(entity.getId(), entity.getToken());
		} catch (DomainError e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}
	
	public void reset(ResetPasswordDTO dto) {
		try {
			UserEntity entityFound = repository.findByUsernameAndToken(dto.username(), dto.token())
					.orElseThrow(() -> new InvalidPasswordResetTokenException(HttpCode.conflict()));
			
			boolean isExpired = LocalDateTime.now().isAfter(entityFound.getTokenExpirationDate());
			
			if(isExpired) throw new InvalidPasswordResetTokenException(HttpCode.conflict());
			
			entityFound.setPassword(BCryptPasswordEncoderUtil.hashPassword(dto.password()));
			entityFound.setToken(null);
			entityFound.setTokenExpirationDate(null);
			
			repository.save(entityFound);
		} catch (DomainError e) {
			LOG.info(e.getMessage(), e);
			throw e;
		}
	}

}
