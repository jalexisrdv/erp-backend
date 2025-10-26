package com.erp.shared.exeption;

public class UnexpectedException extends RuntimeException {

	public static final String MESSAGE = "Internal server error";

	public UnexpectedException() {
		super(MESSAGE);
	}
	
}