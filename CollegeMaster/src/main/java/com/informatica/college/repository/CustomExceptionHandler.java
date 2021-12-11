package com.informatica.college.repository;

import org.bson.json.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.informatica.college.model.ExceptionResponse;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(HttpClientErrorException.class)
	public final ResponseEntity<String> handleAllExceptions(HttpClientErrorException e, WebRequest request) {
		ExceptionResponse excResponse = new ExceptionResponse();
		excResponse.setType("Failure");
		excResponse.setMessage(String.valueOf(e.getRawStatusCode()));
		excResponse.setDuplicated("false");
		
		ObjectMapper mapper = new ObjectMapper();
		String excRes = "";
		try {
			excRes = mapper.writeValueAsString(excResponse);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return new ResponseEntity<String>(excRes,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<String> handleAllExceptions(Exception e, WebRequest request) {
		ExceptionResponse excResponse = new ExceptionResponse();
		excResponse.setType("Failure");
		excResponse.setMessage("Exception");
		excResponse.setDuplicated("false");
		
		ObjectMapper mapper = new ObjectMapper();
		String excRes = "";
		try {
			excRes = mapper.writeValueAsString(excResponse);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return new ResponseEntity<String>(excRes,HttpStatus.BAD_REQUEST);
	}
}
