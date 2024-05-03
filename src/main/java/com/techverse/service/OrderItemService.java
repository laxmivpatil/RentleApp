package com.techverse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.model.OrderItem;
import com.techverse.repository.OrderItemRepository;

@Service
public class OrderItemService  {
	
	@Autowired
	private OrderItemRepository orderItemRepository;

	 
	public OrderItem createOrderItem(OrderItem orderItem) {
		 
		return orderItemRepository.save(orderItem);
		 
	}
	
	

}