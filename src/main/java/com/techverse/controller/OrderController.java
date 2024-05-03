package com.techverse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techverse.exception.OrderException;
import com.techverse.exception.UserException;
 
import com.techverse.model.Order;
import com.techverse.model.User;
import com.techverse.response.ApiResponse;
import com.techverse.service.OrderService;
import com.techverse.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	
	
	@PostMapping("/")
	public ResponseEntity<Order> createOrder(@RequestBody String shippingAddress,@RequestHeader("Authorization") String jwt)throws UserException{
		
		User user =userService.findUserProfileByJwt(jwt).get();
		Order order=orderService.createOrder(user, shippingAddress);
		
		 return new ResponseEntity<Order>(order,HttpStatus.CREATED);
		
		
	}
	@GetMapping("/user")
	public ResponseEntity<List<Order>> userOrderHistory(@RequestHeader("Authorization") String jwt)throws UserException{
		
		User user =userService.findUserProfileByJwt(jwt).get();
		List<Order> order=orderService.usersOrderHistory(user.getId());
		
		 return new ResponseEntity<>(order,HttpStatus.OK);
		
		
	}
	
	
	@GetMapping("/{Id}")
	public ResponseEntity<Order> findOrderById(@PathVariable("Id") Long orderId,
			@RequestHeader("Authorization") String jwt)throws UserException,OrderException{
		
		User user =userService.findUserProfileByJwt(jwt).get();
		
		Order order=orderService.findOrderById(orderId);
		
		 return new ResponseEntity<>(order,HttpStatus.OK);
		
		
	}
	@DeleteMapping("/{Id}")
	public ResponseEntity<ApiResponse> deleteOrderById(@PathVariable("Id") Long orderId,
			@RequestHeader("Authorization") String jwt)throws UserException,OrderException{
		
		User user =userService.findUserProfileByJwt(jwt).get();
		
		 orderService.deleteOrder(orderId);
		
		 ApiResponse res=new ApiResponse();
		 res.setMessage("order deleted successfully");
		 res.setStatus(true);
		 return new ResponseEntity<>(res,HttpStatus.OK);
		
		
	}
	

}

