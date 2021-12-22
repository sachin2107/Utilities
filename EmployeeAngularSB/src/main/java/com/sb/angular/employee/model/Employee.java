package com.sb.angular.employee.model;

public class Employee {

	private long id;

	private String name;

	private String designation;

	private int age;

	public Employee() {
		super();
	}

	public Employee(long id, String name, String designation, int age) {
		super();
		this.id = id;
		this.name = name;
		this.designation = designation;
		this.age = age;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
