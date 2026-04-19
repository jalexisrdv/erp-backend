package com.erp.authentication.service;

import com.erp.authentication.configuration.UserDetailsImpl;
import com.erp.authentication.dto.LoginResponseDTO;
import com.erp.authentication.entity.RefreshTokenEntity;
import com.erp.authentication.exception.InvalidCredentialsException;
import com.erp.shared.domain.DomainError;
import com.erp.user.exception.UserDoesNotExistException;
import com.erp.shared.domain.DomainErrorType;
import com.erp.user.entity.UserEntity;
import com.erp.user.repository.UserRepository;
import com.erp.authentication.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
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
		} catch (AuthenticationException e) {
			LOG.info(e.getMessage(), e);
			throw new InvalidCredentialsException();
		}
	}

	public LoginResponseDTO refreshToken(String refreshToken) {
		try {
			RefreshTokenEntity refreshTokenEntity = refreshTokenService.update(refreshToken);

			UserEntity user = repository.findWithRolesAndPermissionsById(refreshTokenEntity.getUserId()).orElseThrow(() -> new UserDoesNotExistException(DomainErrorType.DEPENDENCY));

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
