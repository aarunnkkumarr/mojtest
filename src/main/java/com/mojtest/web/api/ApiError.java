package com.mojtest.web.api;

public class ApiError {

	private String message;
	
	private String developerMessage;
	
	public ApiError(String message, String developerMessage) {
		super();
		this.message = message;
		this.developerMessage = developerMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}
	
	
	
}
