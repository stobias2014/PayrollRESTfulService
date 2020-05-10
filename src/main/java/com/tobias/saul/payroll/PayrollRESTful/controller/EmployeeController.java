package com.tobias.saul.payroll.PayrollRESTful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobias.saul.payroll.PayrollRESTful.model.Employee;
import com.tobias.saul.payroll.PayrollRESTful.service.EmployeeService;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/employees")
	public List<Employee> getEmployees() {
		return employeeService.findAllEmployees();
	}
	
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeService.createEmployee(employee);
	}
	
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable("employeeId") Long employeeId) {
		return employeeService.findEmployee(employeeId);
	}
	
	@PutMapping("/employees/{employeeId}")
	public Employee updateEmployee(@RequestBody Employee employee, 
			@PathVariable("employeeId") Long employeeId) {
		return employeeService.updateEmployee(employee, employeeId);
	}
	
	@DeleteMapping("/employees/{employeeId}")
	public void deleteEmployee(@PathVariable("employeeId") Long employeeId) {
		employeeService.deleteEmployee(employeeId);
	}

}
