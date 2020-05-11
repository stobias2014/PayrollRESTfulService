package com.tobias.saul.payroll.PayrollRESTful.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.tobias.saul.payroll.PayrollRESTful.controller.OrderController;
import com.tobias.saul.payroll.PayrollRESTful.model.Order;
import com.tobias.saul.payroll.PayrollRESTful.model.OrderStatus;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>>{

	@Override
	public EntityModel<Order> toModel(Order order) {
		
		EntityModel<Order> orderModel = new EntityModel<Order>(order, 
				linkTo(methodOn(OrderController.class).getOrder(order.getId())).withSelfRel(),
				linkTo(methodOn(OrderController.class).getOrders()).withRel("orders"));
		
		if(order.getOrderStatus() == OrderStatus.IN_PROGRESS) {
			orderModel.add(linkTo(methodOn(OrderController.class).cancelOrder(order.getId()))
					.withRel("cancel"));
			orderModel.add(linkTo(methodOn(OrderController.class).completeOrder(order.getId()))
					.withRel("complete"));
		}
		
		return orderModel;
	}
	
	

}
