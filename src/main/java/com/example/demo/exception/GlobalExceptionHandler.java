package com.example.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceException(ResourceNotFoundException ex)
	{
		return new ResponseEntity<>
		      (new ErrorResponse(ex.getMessage(),404),HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleRuntimeException(RuntimeException ex)
	{
		return new ResponseEntity<>
		   (new ErrorResponse(ex.getMessage(),500),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMetthodException(MethodArgumentNotValidException ex)
	{
		Map<String,String> errors = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors().forEach(error->
		   errors.put(error.getField(), error.getDefaultMessage()));
		
		return new  ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception ex)  
	{
		return new  ResponseEntity<>(new ErrorResponse("Something Went Wrong",500),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
