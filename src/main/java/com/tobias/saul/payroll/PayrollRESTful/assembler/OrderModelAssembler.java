package com.tobias.saul.payroll.PayrollRESTful.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.tobias.saul.payroll.PayrollRESTful.model.Order;

public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>>{

	@Override
	public EntityModel<Order> toModel(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

}
