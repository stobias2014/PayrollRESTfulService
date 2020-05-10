package com.tobias.saul.payroll.PayrollRESTful.controller;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

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
	public CollectionModel<EntityModel<Employee>> getEmployees() {

		List<EntityModel<Employee>> employees = employeeService.findAllEmployees().stream()
				.map(employee -> new EntityModel<Employee>(employee,
						linkTo(methodOn(EmployeeController.class).getEmployee(employee.getId())).withSelfRel(),
						linkTo(methodOn(EmployeeController.class).getEmployees()).withRel("employees")))
				.collect(Collectors.toList());

		return new CollectionModel<>(employees,
				linkTo(methodOn(EmployeeController.class).getEmployees()).withSelfRel());
	}

	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeService.createEmployee(employee);
	}

	@GetMapping("/employees/{employeeId}")
	public EntityModel<Employee> getEmployee(@PathVariable("employeeId") Long employeeId) {
		Employee employee = employeeService.findEmployee(employeeId);
		return new EntityModel<>(employee,
				linkTo(methodOn(EmployeeController.class).getEmployee(employeeId)).withSelfRel(),
				linkTo(methodOn(EmployeeController.class).getEmployees()).withRel("employees"));
	}

	@PutMapping("/employees/{employeeId}")
	public Employee updateEmployee(@RequestBody Employee employee, @PathVariable("employeeId") Long employeeId) {
		return employeeService.updateEmployee(employee, employeeId);
	}

	@DeleteMapping("/employees/{employeeId}")
	public void deleteEmployee(@PathVariable("employeeId") Long employeeId) {
		employeeService.deleteEmployee(employeeId);
	}

}
