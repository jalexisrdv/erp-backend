package com.erp.authentication.service;

import com.erp.authentication.configuration.UserDetailsImpl;
import com.erp.authentication.dto.SignInResponseDTO;
import com.erp.authentication.exception.InvalidCredentialsException;
import com.erp.authentication.repository.UserRepository;
import com.erp.authentication.util.JwtUtil;
import com.erp.shared.domain.HttpCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SignInService {
	
	private final static Logger LOG = LoggerFactory.getLogger(SignInService.class);
	
	private final AuthenticationManager authenticationManager;

	public SignInService(AuthenticationManager authenticationManager, UserRepository repository) {
		this.authenticationManager = authenticationManager;
	}
	
	public SignInResponseDTO signIn(String username, String password) {
		try {
			Authentication unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
			Authentication authentication = authenticationManager.authenticate(unauthenticated);

			UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();

			boolean isTemporalPasswordExpired = LocalDateTime.now().isAfter(details.getTokenExpirationDate());

			if(isTemporalPasswordExpired) {
				throw new InvalidCredentialsException(HttpCode.conflict());
			}

			return new SignInResponseDTO(
					username,
					details.getRoles(),
					details.getPermissions(),
					JwtUtil.generateToken(username),
					details.shouldResetPassword()
			);
		} catch (AuthenticationException e) {
			LOG.info(e.getMessage(), e);
			throw new InvalidCredentialsException(HttpCode.badRequest());
		}
	}

}
