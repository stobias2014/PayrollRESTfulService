package com.tobias.saul.payroll.PayrollRESTful.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.tobias.saul.payroll.PayrollRESTful.controller.EmployeeController;
import com.tobias.saul.payroll.PayrollRESTful.model.Employee;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>>{

	@Override
	public EntityModel<Employee> toModel(Employee employee) {
		return new EntityModel<Employee>(employee,
				linkTo(methodOn(EmployeeController.class).getEmployee(employee.getId())).withSelfRel(),
				linkTo(methodOn(EmployeeController.class).getEmployees()).withRel("employees"));
	}
	
}
