package com.informatica.college.feignproxyinterface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="employee-mongo-database", url="localhost:8088/mongocrud")
public interface EmployeeMongoDbProxy {

	@GetMapping("/employee/exc/{id}")
	public ResponseEntity<String> getEmployeeExc(@PathVariable("id") Long id);
}
