package org.learn.mongo.database.controller;

import org.learn.mongo.database.model.Employee;
import org.learn.mongo.database.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/employee/save")
	public void saveEmployee() {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setFirstName("sachin");
		employee.setLastName("khachane");
		employeeService.saveEmployee(employee);
		System.out.println("Employee saved with id="+employee.getId());
	}
}
