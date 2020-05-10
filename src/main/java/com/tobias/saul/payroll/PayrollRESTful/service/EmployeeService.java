package com.tobias.saul.payroll.PayrollRESTful.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobias.saul.payroll.PayrollRESTful.exception.EmployeeNotFoundException;
import com.tobias.saul.payroll.PayrollRESTful.model.Employee;
import com.tobias.saul.payroll.PayrollRESTful.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository empRepo;
	
	public List<Employee> findAllEmployees() {
		return empRepo.findAll();
	}
	
	public Employee findEmployee(Long employeeId) {
		return empRepo.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException(employeeId));
	}
	
	public Employee createEmployee(Employee employee) {
		return empRepo.save(employee);
	}
	
	public Employee updateEmployee(Employee newEmployee, Long employeeId) {
		return empRepo.findById(employeeId)
				.map(employee -> {
					employee.setName(employee.getName());
					employee.setRole(employee.getRole());
					return empRepo.save(employee);
				})
				.orElseGet(() -> {
					newEmployee.setId(employeeId);
					return empRepo.save(newEmployee);
				});
	}
	
	public void deleteEmployee(Long employeeId) {
		empRepo.deleteById(employeeId);
	}
	

}
