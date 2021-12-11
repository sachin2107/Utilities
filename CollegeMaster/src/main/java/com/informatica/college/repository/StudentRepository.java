package com.informatica.college.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.informatica.college.model.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, Long>{

}
