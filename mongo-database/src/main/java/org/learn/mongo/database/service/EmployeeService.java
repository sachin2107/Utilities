package org.learn.mongo.database.service;

import org.learn.mongo.database.model.Employee;
import org.learn.mongo.database.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service("EmployeeService")
public class EmployeeService implements EmployeeRepository{

	@Autowired
	MongoTemplate mongoTemplate;
	
	public void saveEmployee(Employee employee) {
		mongoTemplate.save(employee, "employee");
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
}
