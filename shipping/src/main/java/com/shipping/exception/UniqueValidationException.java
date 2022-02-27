package com.shipping.exception;

public class UniqueValidationException extends RuntimeException{
	String message;
	public UniqueValidationException(String message) {
		super(message);
		this.message = message;
	}
}
