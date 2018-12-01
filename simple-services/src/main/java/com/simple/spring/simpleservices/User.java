package com.simple.spring.simpleservices;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.json.simple.JSONObject;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private Double salary;

	public User() {
	}

	public User(String name, Double salary) {
		this.setName(name);
		this.setSalary(salary);
	}

	public User(int id, String name, Double salary) {
		this.setId(id);
		this.setName(name);
		this.setSalary(salary);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		// print format for json
		JSONObject user = new JSONObject();
		user.put("name", name);
		user.put("salary", salary);
		return user.toJSONString();
	}
}