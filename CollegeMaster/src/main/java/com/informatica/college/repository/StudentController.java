package com.informatica.college.repository;

import java.util.Arrays;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.informatica.college.constants.AppConstants;
import com.informatica.college.feignproxyinterface.EmployeeMongoDbProxy;
import com.informatica.college.model.FinalResponse;
import com.informatica.college.model.Student;

@RestController
@RequestMapping("/college/master")
public class StudentController {

	private static final Logger logger = LogManager.getLogger(StudentController.class);

	@Autowired
	private StudentRepository studRepo;

	@Autowired
	private EmployeeMongoDbProxy proxy;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@PostMapping("/student/{id}")
	public void saveStudent(@RequestBody Student student, @PathVariable Long id) {
		student.setId(id);
		logger.debug("Inside saveStudent method...");
		studRepo.save(student);
		logger.debug("Student saved with id=[{}]", student.getId());
	}

	@GetMapping("/student/{id}")
	public ResponseEntity<FinalResponse> getStudentById(@PathVariable Long id) {
		MultiValueMap<String, String> headersMap = new HttpHeaders();
		headersMap.put("keys", Arrays.asList("value"));

		Optional<Student> student = studRepo.findById(id);

		ResponseEntity<FinalResponse> responseEntity = null;
		HttpEntity<FinalResponse> httpEntity = null;
		FinalResponse finalResponse = new FinalResponse();
		if (student.isPresent()) {
			finalResponse.setHttpCode(String.valueOf(HttpStatus.OK));
			finalResponse.setResponseMessage(student.get());
			finalResponse.getMap().put(AppConstants.RESPONSE_BODY, student);
			httpEntity = new HttpEntity<FinalResponse>(finalResponse, headersMap);
			ResponseEntity responseEntity2 = null;
			try {
				responseEntity2 = restTemplate().exchange("http://localhost:8088/mongocrud/employee/" + id,
						HttpMethod.GET, httpEntity, FinalResponse.class);
				if (null != responseEntity2) {
					String empHttpCode = String.valueOf(responseEntity2.getStatusCodeValue());
					String empRespBody = responseEntity2.getBody().toString();
					finalResponse.getMap().put("EMP_HTTP_CODE", empHttpCode);
					finalResponse.getMap().put("EMP_RESP_BODY", empRespBody);
					finalResponse.setResponseMessage(empRespBody);
				}
			} catch (Exception e) {
				System.out.println("inside exception: " + e.getMessage());
				throw new RuntimeException();
			}
			responseEntity = responseEntity2;
//			responseEntity = new ResponseEntity<FinalResponse>(finalResponse, headersMap, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<FinalResponse>(finalResponse, headersMap, HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}

	@GetMapping("/student/exception/{id}")
	public ResponseEntity<String> getStudentByexceptionId(@PathVariable Long id) {
		MultiValueMap<String, String> headersMap = new HttpHeaders();
		headersMap.put("keys", Arrays.asList("value"));

		Optional<Student> student = studRepo.findById(id);

		ResponseEntity<String> responseEntity = null;
		HttpEntity<String> httpEntity = null;
		if (student.isPresent()) {
			httpEntity = new HttpEntity<String>(student.get().getFirstName(), headersMap);
			try {
//				responseEntity = restTemplate().exchange("http://localhost:8088/mongocrud/employee/exc1/" + id,
//						HttpMethod.GET, httpEntity, String.class);
				//instead of above we can use feign client to call service
				responseEntity = proxy.getEmployeeExc(id);
				if (null != responseEntity) {
					String empHttpCode = String.valueOf(responseEntity.getStatusCodeValue());
					String empRespBody = responseEntity.getBody().toString();
					return responseEntity;
				}
			}catch(HttpClientErrorException clientErrorException) {
				System.out.println("HTTP status code clientErrorException="+clientErrorException.getRawStatusCode());
				return new CustomExceptionHandler().handleAllExceptions(clientErrorException, null);
			}catch(Exception e) {
				System.out.println("Inside exception...");
				return new CustomExceptionHandler().handleAllExceptions(e, null);
			}
			System.out.println("method ends...");
		} else {
			responseEntity = new ResponseEntity<String>("error", headersMap, HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}
}
