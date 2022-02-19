package com.projectsapi.demo.exception;

public class NotFoundException extends RuntimeException{
    private static final long serialVersionUID = 7440191990283265752L;
	
	private String message;
	
	public NotFoundException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
