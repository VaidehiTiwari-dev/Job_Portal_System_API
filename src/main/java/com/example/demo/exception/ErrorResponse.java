package com.example.demo.exception;

public class ErrorResponse {

	private String message;
	private int status;
	
	public ErrorResponse(String message, int status) {
		super();
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}
	
	
}
