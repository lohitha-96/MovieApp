package com.optum.exception;

import java.util.List;

public class ErrorResponse {

	private List<String> details;
	private String message;

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ErrorResponse(List<String> details, String message) {
		super();
		this.details = details;
		this.message = message;
	}

	public ErrorResponse() {
		super();
	}

}
