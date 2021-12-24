package com.sb.angular.employee.model;

import java.util.Objects;

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

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", designation=" + designation + ", age=" + age + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, designation, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return age == other.age && Objects.equals(designation, other.designation) && id == other.id
				&& Objects.equals(name, other.name);
	}

	
}
