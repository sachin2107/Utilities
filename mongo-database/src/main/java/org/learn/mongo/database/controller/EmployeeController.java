package org.learn.mongo.database.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.learn.mongo.database.AppConstants;
import org.learn.mongo.database.model.Employee;
import org.learn.mongo.database.model.FinalResponse;
import org.learn.mongo.database.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.result.DeleteResult;

@RestController
@RequestMapping(value = "/mongocrud")
public class EmployeeController {

	private static final Logger logger = LogManager.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/employee/{id}")
	public void saveEmployee(@RequestBody Employee employee, @PathVariable Long id) {
		employee.setId(id);
		logger.debug("Inside saveEmployee method...");
		employeeService.saveEmployee(employee);
		logger.debug("Employee saved with id=[{}]", employee.getId());
	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<FinalResponse> getEmployeeById(@PathVariable Long id) {
		MultiValueMap<String, String> headersMap = new HttpHeaders();
		headersMap.put("keys", Arrays.asList("value"));

		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		List<Employee> employee = employeeService.getMongoTemplate().find(query, Employee.class);

		ResponseEntity<FinalResponse> responseEntity = null;
		HttpEntity<FinalResponse> httpEntity = null;
		FinalResponse finalResponse = new FinalResponse();
		if (null != employee) {
			finalResponse.setHttpCode(String.valueOf(HttpStatus.OK));
			finalResponse.setResponseMessage(employee);
			finalResponse.getMap().put(AppConstants.RESPONSE_BODY, employee);
			new HttpEntity<FinalResponse>(finalResponse, headersMap);
			// responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
			// String.class);
			responseEntity = new ResponseEntity<FinalResponse>(finalResponse, headersMap, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<FinalResponse>(finalResponse, headersMap, HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}

	@GetMapping("/employees")
	public ResponseEntity<FinalResponse> getEmployees() {
		MultiValueMap<String, String> headersMap = new HttpHeaders();
		headersMap.put("keys", Arrays.asList("value"));

		List<Employee> employees = employeeService.getMongoTemplate().findAll(Employee.class);

		ResponseEntity<FinalResponse> responseEntity = null;
		HttpEntity<FinalResponse> httpEntity = null;
		FinalResponse finalResponse = new FinalResponse();
		if (null != employees && !employees.isEmpty()) {
			finalResponse.setHttpCode(String.valueOf(HttpStatus.OK));
			finalResponse.setResponseMessage(employees);
			new HttpEntity<FinalResponse>(finalResponse, headersMap);
			responseEntity = new ResponseEntity<FinalResponse>(finalResponse, headersMap, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<FinalResponse>(finalResponse, headersMap, HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
	
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<FinalResponse> deleteEmployee(@PathVariable Long id){
		MultiValueMap<String, String> headersMap = new HttpHeaders();
		headersMap.put("keys", Arrays.asList("value"));

		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		DeleteResult deletedEmployees = employeeService.getMongoTemplate().remove(query, Employee.class);
		long deletedEmployeesCnt = deletedEmployees.getDeletedCount();
		ResponseEntity<FinalResponse> responseEntity = null;
		HttpEntity<FinalResponse> httpEntity = null;
		FinalResponse finalResponse = new FinalResponse();
		finalResponse.setHttpCode(String.valueOf(HttpStatus.OK));
		finalResponse.setResponseMessage("No. of employees removed="+deletedEmployeesCnt);
		new HttpEntity<FinalResponse>(finalResponse, headersMap);
		responseEntity = new ResponseEntity<FinalResponse>(finalResponse, headersMap, HttpStatus.OK);
		return responseEntity;
	}
}
