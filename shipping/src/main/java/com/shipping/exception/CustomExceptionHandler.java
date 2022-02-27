package com.shipping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@ControllerAdvice
public class CustomExceptionHandler extends DefaultHandlerExceptionResolver{
	@ExceptionHandler(value=UniqueValidationException.class)
	public ResponseEntity<Object> exception(UniqueValidationException ex){
		return new ResponseEntity<>("Duplicate Entry:Product Order pair already exist",HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value=ProductNotFoundException.class)
	public ResponseEntity<Object> exception(ProductNotFoundException ex){
		return new ResponseEntity<>(ex.getMeassage(),HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(value=IllegalArgumentException.class)
	public ResponseEntity<Object> exception(IllegalArgumentException ex){
		ex.printStackTrace();
		return new ResponseEntity<>("Enter correct arguments: "+ex.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
	}
}
