package com.sb.angular.student.studentcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.angular.student.model.Student;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api")
public class StudentController {

	private List<Student> students = createStudents();

	private List<Student> createStudents() {
		return Arrays.asList(new Student(1, "sachin", "khachane"), new Student(2, "ankita", "rane"));
	}

	@GetMapping(value = "/students", produces = "application/json")
	public List<Student> getStudents() {
		System.out.println("/students initialized...");
		return students;
	}

	@GetMapping(produces = "application/json")
	public List<Student> firstPage() {
		System.out.println("first page initialized...");
		return students;
	}

	@DeleteMapping(value = "/deleteStudent/{firstName}")
	public Student deleteByFirstNameStudent(@PathVariable("firstName") String firstName) {
		System.out.println("delete student initialized...");
		Student deletedStudent = null;
		for (Student student1 : students) {
			if (student1.getFirstName().equals(firstName)) {
				deletedStudent = student1;
			}
		}
		return deletedStudent;
	}

	@DeleteMapping(value = "/deletestudent/{id}")
	public Student deleteStudentById(@PathVariable("id") int id) {
		System.out.println("delete student initialized...");
		Student deletedStudent = null;
		for (Student student1 : students) {
			if (student1.getId() == id) {
				deletedStudent = student1;
			}
		}
		return deletedStudent;
	}
}
