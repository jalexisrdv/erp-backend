package com.erp.authentication.service;

import java.util.UUID;

import com.erp.authentication.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PasswordTokenGenerator {

	private final static Logger LOG = LoggerFactory.getLogger(PasswordTokenGenerator.class);

	private final UserRepository repository;

	public PasswordTokenGenerator(UserRepository repository) {
		this.repository = repository;
	}

	public static String generate() {
		return UUID.randomUUID().toString();
	}


}
