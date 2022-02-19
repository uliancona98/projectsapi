package com.projectsapi.demo.exception;

public class UnprocessableEntityException extends RuntimeException{
    private static final long serialVersionUID = 7740191990283265752L;
	
	private String message;
	
	public UnprocessableEntityException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

