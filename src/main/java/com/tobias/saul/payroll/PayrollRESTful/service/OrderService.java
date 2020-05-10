package com.tobias.saul.payroll.PayrollRESTful.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobias.saul.payroll.PayrollRESTful.exception.OrderNotFoundException;
import com.tobias.saul.payroll.PayrollRESTful.model.Order;
import com.tobias.saul.payroll.PayrollRESTful.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	public List<Order> findAllOrders() {
		return orderRepository.findAll();
	}
	
	public Order findOrder(Long orderId) {
		return orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException(orderId));
	}
	
	public Order createOrder(Order order) {
		return orderRepository.save(order);
	}
	
	public Order updateOrder(Order newOrder, Long orderId) {
		return orderRepository.findById(orderId)
				.map(order -> {
					order.setDescription(newOrder.getDescription());
					order.setOrderStatus(newOrder.getOrderStatus());
					return orderRepository.save(order);
				})
				.orElseGet(() ->{
					newOrder.setId(orderId);
					return orderRepository.save(newOrder);
				});
	}
	
	public void deleteOrder(Long orderId) {
		orderRepository.deleteById(orderId);
	}
	
	

}
