package com.techverse.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.exception.OrderException;
import com.techverse.model.Cart;
import com.techverse.model.CartItem;
import com.techverse.model.Order;
import com.techverse.model.OrderItem;
import com.techverse.model.User; 
import com.techverse.repository.CartRepository;
import com.techverse.repository.OrderItemRepository;
import com.techverse.repository.OrderRepository;
import com.techverse.repository.UserRepository;

@Service
public class OrderService {
	

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserRepository userRepository;
	
	 @Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderItemService orderItemSrvice;
	public Order createOrder(User user, String shippAddress) {
 	Cart cart =cartService.findUserCart(user.getId());
		List<OrderItem> orderItems=new ArrayList<>();
		
		for(CartItem item:cart.getCartItems())
		{
			OrderItem orderItem=new OrderItem();
			
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());
			
			
			OrderItem createdOrderItem=orderItemRepository.save(orderItem);
			
			orderItems.add(createdOrderItem);
		}
		
		Order createdOrder=new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setToatalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDicountedPrice());
		createdOrder.setDiscounte(cart.getDiscounte());
		createdOrder.setTotalItem(cart.getTotalItem());
		
		createdOrder.setShippingAddress(shippAddress);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setStatus("PENDING");
		createdOrder.setCreatedAt(LocalDateTime.now());
		
		Order savedOrder=orderRepository.save(createdOrder);
		
		
		for(OrderItem item: orderItems) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}
		
		return savedOrder;
	}

	 
	public Order findOrderById(Long orderId) throws OrderException {
		 Optional<Order> opt=orderRepository.findById(orderId);
		 if(opt.isPresent())
		 {
		return opt.get();
		 }
		 throw new OrderException("Order not Exist with id "+orderId);
	}

	 
	public List<Order> usersOrderHistory(Long userId) {
		List<Order> orders=orderRepository.getUsersOreders(userId);
		return orders;
	}

 
	public Order placedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");
		
		return orderRepository.save(order);
		
	 
	}

	 
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		 
		return orderRepository.save(order);
	}

	 
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		 
		return orderRepository.save(order);
		 
	}

 
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		 
		return orderRepository.save(order);
		 
	}

	 
	public Order cancledOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("CANCELLED");
		 
		return orderRepository.save(order);
		 
	}

 
	public List<Order> getAllOrders() {
		 
		return orderRepository.findAll();
	}

	 
	public void deleteOrder(Long orderId) throws OrderException { 
		
		Order order=findOrderById(orderId);
		orderRepository.deleteById(orderId);
		
	}

}

