package com.tobias.saul.payroll.PayrollRESTful.exception;

public class EmployeeNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2762222345181071349L;
	
	public EmployeeNotFoundException(Long employeeId) {
		super("Employee " + employeeId + " not found.");
	}
}
