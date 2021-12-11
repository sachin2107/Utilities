package com.informatica.college.model;

public class ExceptionResponse {

	private String type;
	
	private String message;
	
	private String duplicated;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDuplicated() {
		return duplicated;
	}

	public void setDuplicated(String duplicated) {
		this.duplicated = duplicated;
	}
	
}
