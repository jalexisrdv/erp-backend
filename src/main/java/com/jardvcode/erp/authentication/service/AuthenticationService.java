package com.jardvcode.erp.authentication.service;

import com.jardvcode.erp.authentication.configuration.UserDetailsImpl;
import com.jardvcode.erp.authentication.dto.LoginResponseDTO;
import com.jardvcode.erp.authentication.exception.PasswordExpiredException;
import com.jardvcode.erp.authentication.entity.RefreshTokenEntity;
import com.jardvcode.erp.authentication.exception.InvalidCredentialsException;
import com.jardvcode.erp.shared.domain.DomainError;
import com.jardvcode.erp.users.exception.UserDoesNotExistException;
import com.jardvcode.erp.shared.domain.DomainErrorType;
import com.jardvcode.erp.users.entity.UserEntity;
import com.jardvcode.erp.users.repository.UserRepository;
import com.jardvcode.erp.authentication.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
	
	private final static Logger LOG = LoggerFactory.getLogger(AuthenticationService.class);
	
	private final AuthenticationManager authenticationManager;
	private final RefreshTokenService refreshTokenService;
	private final UserRepository repository;

	public AuthenticationService(AuthenticationManager authenticationManager, UserRepository repository, RefreshTokenService refreshTokenService) {
		this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.repository = repository;
    }

	public LoginResponseDTO login(String username, String password) {
		try {
			Authentication unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
			Authentication authentication = authenticationManager.authenticate(unauthenticated);

			UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();

			String refreshToken = refreshTokenService.create(details.getId()).getToken();

			return new LoginResponseDTO(
					JwtUtil.generateToken(username, details.claims()),
					refreshToken
			);
		} catch(CredentialsExpiredException e) {
			PasswordExpiredException exception = new PasswordExpiredException(username);

			LOG.info(exception.getMessage());
			throw exception;
		} catch (AuthenticationException e) {
			LOG.info(e.getMessage(), e);
			throw new InvalidCredentialsException();
		}
	}

	public LoginResponseDTO refreshToken(String refreshToken) {
		try {
			RefreshTokenEntity refreshTokenEntity = refreshTokenService.update(refreshToken);

			UserEntity user = repository.findWithRolesAndPermissionsById(refreshTokenEntity.getUserId()).orElseThrow(() -> new UserDoesNotExistException(DomainErrorType.CONFLICT));

			UserDetailsImpl details = UserDetailsImpl.create(user);

			return new LoginResponseDTO(
					JwtUtil.generateToken(user.getUsername(), details.claims()),
					refreshTokenEntity.getToken()
			);
		} catch(DomainError e) {
			LOG.info(e.getMessage(), e);
			throw e;
		} catch(Exception e) {
			LOG.info(e.getMessage(), e);
			throw e;
		}
	}

}
