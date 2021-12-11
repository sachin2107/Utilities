package com.informatica.college.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "student")
public class Student {

	private Long id;
	
	private String rollNo;
	
	private String firstName;
	
	private String lastName;
	
	private String studentClass;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", rollNo=" + rollNo + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", studentClass=" + studentClass + "]";
	}
	
	
}
