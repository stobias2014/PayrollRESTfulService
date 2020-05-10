package com.tobias.saul.payroll.PayrollRESTful.model;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class Employee {
	
	private Long id;
	private String name;
	private String role;
	
	public Employee() {}
	
	public Employee(String name, String role) {
		this.name = name;
		this.role = role;
	}

}
