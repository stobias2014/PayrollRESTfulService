package com.tobias.saul.payroll.PayrollRESTful.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tobias.saul.payroll.PayrollRESTful.exception.OrderNotFoundException;

@RestControllerAdvice
public class OrderControllerAdvice {
	
	@ResponseBody
	@ExceptionHandler(OrderNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String orderNotFoundHandler(OrderNotFoundException e) {
		return e.getMessage();
	}

}
