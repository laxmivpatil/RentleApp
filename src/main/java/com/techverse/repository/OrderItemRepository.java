package com.techverse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techverse.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	
	

}
