package com.sb.angular.employee;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.angular.employee.model.Employee;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api")
public class EmployeeController {

	private List<Employee> employees = createEmployeeList();

	private List<Employee> createEmployeeList() {
		List<Employee> emps = new ArrayList<>();
		emps.add(new Employee(1,"sachin","engineer",30));
		emps.add(new Employee(2,"dipak","scientist",40));
		return emps;
	}
	
	@GetMapping(value = {"/","/employees"})
	public List<Employee> getEmployees(){
		return employees;
	}
	
	@GetMapping("/employee/{id}")
	public Employee getEmployee(@PathVariable("id")long id) {
		Employee employee = new Employee();
		for(Employee e:employees) {
			if(e.getId() == id) {
				employee = e;
			}
		}
		return employee;
	}
	
	@PostMapping("/employee")
	public Employee createEmployee(@RequestBody Employee employee) {
		employees.add(employee);
		return employee;
	}
	
	@DeleteMapping("/employee/{id}")
	public Employee deleteEmployee(@PathVariable("id")long id) {
		System.out.println("Inside delete employee...");
		Employee employee = new Employee();
		for(Employee e:employees) {
			if(e.getId() == id) {
				employee = e;
			}
		}
		employees.remove(employee);
		return employee;
	}

	@PutMapping("/employee/{id}")
	public Employee updateEmployee(@RequestBody Employee emp, @PathVariable("id")long id) {
		System.out.println("Inside update employee...");
		Employee employee = new Employee();
		System.out.println(emp.getName());
		for(int i=0; i<employees.size(); i++) {
			employee = employees.get(i);
			if(employee.getId() == id) {
				employees.set(i, emp);
			}
		}
		return employee;
	}
}
