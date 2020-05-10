package com.tobias.saul.payroll.PayrollRESTful.exception;

public class OrderNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4007409851443375648L;
	
	public OrderNotFoundException(Long orderId) {
		super("Order " + orderId + " not found");
	}

}
