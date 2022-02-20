package com.projectsapi.demo.dto;

import javax.validation.constraints.NotNull;

public class ErrorDTO {
	@NotNull
	private String message;

	public ErrorDTO(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}