package com.optum.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice // it get called when exception occur in our application
public class CustomException {

	@ExceptionHandler(Exception.class)	//its for all type of exception
	public ResponseEntity<Object> handlerAnyException(Exception ex, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse res = new ErrorResponse(details, "Server Error");
		return new ResponseEntity<Object>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
