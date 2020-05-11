package com.tobias.saul.payroll.PayrollRESTful.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobias.saul.payroll.PayrollRESTful.assembler.OrderModelAssembler;
import com.tobias.saul.payroll.PayrollRESTful.model.Order;
import com.tobias.saul.payroll.PayrollRESTful.model.OrderStatus;
import com.tobias.saul.payroll.PayrollRESTful.service.OrderService;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

	private final OrderService orderService;
	private final OrderModelAssembler assembler;

	@Autowired
	public OrderController(OrderService orderService, OrderModelAssembler assembler) {
		this.orderService = orderService;
		this.assembler = assembler;
	}

	@GetMapping("/orders/{orderId}")
	public EntityModel<Order> getOrder(@PathVariable("orderId") Long orderId) {
		Order order = orderService.findOrder(orderId);
		
		return assembler.toModel(order);
	}

	@GetMapping("/orders")
	public CollectionModel<EntityModel<Order>> getOrders() {
		
		List<EntityModel<Order>> orders = orderService.findAllOrders().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		
		return new CollectionModel<>(orders,
				linkTo(methodOn(OrderController.class).getOrders()).withSelfRel());
	}
	
	@PostMapping("/orders")
	public ResponseEntity<?> createOrder(@RequestBody Order order) {
		
		order.setOrderStatus(OrderStatus.IN_PROGRESS);

		EntityModel<Order> orderModel = assembler
				.toModel(orderService.createOrder(order));

		return ResponseEntity.created(orderModel.getRequiredLink(IanaLinkRelations.SELF)
				.toUri()).body(orderModel);
	}
	
	@PutMapping("/orders/{orderId}")
	public ResponseEntity<?> updateOrder(@RequestBody Order newOrder, @PathVariable("orderId") Long orderId) {
		
		EntityModel<Order> orderModel = assembler
				.toModel(orderService.updateOrder(newOrder, orderId));
		
		return ResponseEntity.created(orderModel.getRequiredLink(IanaLinkRelations.SELF)
				.toUri()).body(orderModel);
	}
	
	@PutMapping("/orders/{orderId}/complete")
	public ResponseEntity<?> completeOrder(@PathVariable("orderId") Long orderId) {
		
		Order order = orderService.findOrder(orderId);
		
		if(order.getOrderStatus() == OrderStatus.IN_PROGRESS) {
			order.setOrderStatus(OrderStatus.COMPLETED);
			return ResponseEntity.ok(assembler.toModel(orderService.createOrder(order)));
		}
		
		return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body(new VndErrors("Method not allowed", "Cannot complete order" +
				" that is in " + order.getOrderStatus() + " status."));
	}
	
	@DeleteMapping("/orders/{orderId}/cancel")
	public ResponseEntity<?> cancelOrder(@PathVariable("orderId") Long orderId) {
		
		Order order = orderService.findOrder(orderId);
		
		if(order.getOrderStatus() == OrderStatus.IN_PROGRESS) {
			order.setOrderStatus(OrderStatus.CANCELLED);
			return ResponseEntity.ok(assembler.toModel(orderService.createOrder(order)));
		}
		
		return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body(new VndErrors("Method not allowed", "Cannot cancel order " +
				" that is in " + order.getOrderStatus() + "status."));
	}
	
	@DeleteMapping("/orders/{orderId}")
	public ResponseEntity<?> deleteOrder(@PathVariable("orderId") Long orderId) {
		
		orderService.deleteOrder(orderId);
		
		return ResponseEntity.noContent().build();
	}

}
