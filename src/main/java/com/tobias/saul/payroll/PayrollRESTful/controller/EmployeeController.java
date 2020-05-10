package com.tobias.saul.payroll.PayrollRESTful.controller;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobias.saul.payroll.PayrollRESTful.assembler.EmployeeModelAssembler;
import com.tobias.saul.payroll.PayrollRESTful.model.Employee;
import com.tobias.saul.payroll.PayrollRESTful.service.EmployeeService;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	private final EmployeeService employeeService;
	private final EmployeeModelAssembler assembler;
	
	@Autowired
	public EmployeeController(EmployeeService employeeService, EmployeeModelAssembler assembler) {
		this.employeeService = employeeService;
		this.assembler = assembler;
		
	}

	@GetMapping("/employees")
	public CollectionModel<EntityModel<Employee>> getEmployees() {

		List<EntityModel<Employee>> employees = employeeService.findAllEmployees().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());

		return new CollectionModel<>(employees,
				linkTo(methodOn(EmployeeController.class).getEmployees()).withSelfRel());
	}

	@PostMapping("/employees")
	public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
		
		EntityModel<Employee> employeeModel = assembler
				.toModel(employeeService.createEmployee(employee));
		
		return ResponseEntity.created(employeeModel.getRequiredLink(IanaLinkRelations.SELF)
				.toUri()).body(employeeModel);
	}

	@GetMapping("/employees/{employeeId}")
	public EntityModel<Employee> getEmployee(@PathVariable("employeeId") Long employeeId) {		
		Employee employee = employeeService.findEmployee(employeeId);
		return assembler.toModel(employee);		
	}

	@PutMapping("/employees/{employeeId}")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee, @PathVariable("employeeId") Long employeeId) {
		
		EntityModel<Employee> employeeModel = assembler.toModel(employeeService.updateEmployee(employee, employeeId));
		
		return ResponseEntity.created(employeeModel.getRequiredLink(IanaLinkRelations.SELF)
				.toUri()).body(employeeModel);
	}

	@DeleteMapping("/employees/{employeeId}")
	public ResponseEntity<?> deleteEmployee(@PathVariable("employeeId") Long employeeId) {
		employeeService.deleteEmployee(employeeId);
		
		return ResponseEntity.noContent().build();
	}

}
