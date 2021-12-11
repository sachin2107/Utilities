package com.informatica.college.model;

import java.util.HashMap;
import java.util.Map;

public class FinalResponse {

	private String httpCode;
	
	private Object responseMessage;
	
	private Map<String, Object> map = new HashMap<String, Object>();

	public String getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(String httpCode) {
		this.httpCode = httpCode;
	}

	public Object getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(Object responseMessage) {
		this.responseMessage = responseMessage;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

}
