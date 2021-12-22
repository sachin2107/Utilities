package com.sb.angular.student.studentcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.angular.student.model.Student;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api")
public class StudentController {

	private List<Student> students = createStudents();

	private List<Student> createStudents() {
		List<Student> al = new ArrayList<>();
		al.add(new Student(1, "sachin", "khachane"));
		al.add(new Student(2, "ankita", "rane"));
		return al;
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
				students.remove(student1);
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
				students.remove(student1);
				deletedStudent = student1;
			}
		}
		return deletedStudent;
	}

	@PostMapping(value = "/student")
	public Student createStudent(@RequestBody Student student) {
		System.out.println("inside create student....");
		students.add(student);
		return student;
	}

	@GetMapping(value = "/student/{id}")
	public Student getStudentById(@PathVariable("id") int id) {
		System.out.println("get student by id initialized...");
		Student stud = new Student();
		for (Student student1 : students) {
			if (student1.getId() == id) {
				stud = student1;
			}
		}
		return stud;
	}

	/*
	 * @GetMapping(value = "/student/{id}") public ResponseEntity<Student>
	 * getStudentByIdUsingRespEntity(@PathVariable("id") int id) {
	 * System.out.println("get student by id initialized...");
	 * ResponseEntity<Student> respEntity = new ResponseEntity<Student>(new
	 * Student(), HttpStatus.NOT_FOUND); Student stud = new Student(); for (Student
	 * student1 : students) { if (student1.getId() == id) { stud = student1;
	 * respEntity = new ResponseEntity<Student>(stud, HttpStatus.FOUND); } } return
	 * respEntity; }
	 */
}
