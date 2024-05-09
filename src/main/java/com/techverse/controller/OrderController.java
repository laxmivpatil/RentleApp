package com.techverse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techverse.exception.OrderException;
import com.techverse.exception.UserException;
import com.techverse.model.CheckoutRequest;
import com.techverse.model.Order;
import com.techverse.model.OrderItem;
import com.techverse.model.User;
import com.techverse.repository.OrderItemRepository;
import com.techverse.repository.OrderRepository;
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
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	
	@PostMapping("/")
	public ResponseEntity<Order> createOrder(@RequestBody String shippingAddress,@RequestHeader("Authorization") String jwt)throws UserException{
		
		User user =userService.findUserProfileByJwt(jwt).get();
		Order order=orderService.createOrder(user, shippingAddress);
 		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	
	
	
	@PutMapping("/checkout")
    public ResponseEntity<Map<String, Object>> checkout(@RequestBody CheckoutRequest checkoutRequest)throws OrderException {
        String paymentSuccess = checkoutRequest.getPaymentStatus();

        // Update order status and payment details if payment is successful
        if (paymentSuccess.equalsIgnoreCase("success")) {
           Order order= orderService.findOrderById(checkoutRequest.getOrderId());
           
           order.getPaymentDetails().setPaymentId(checkoutRequest.getPaymentId());
           order.getPaymentDetails().setPaymentMethod(checkoutRequest.getPaymentMethod());
           order.getPaymentDetails().setRazorpayPaymentId(checkoutRequest.getRazorpayPaymentId());
           order.getPaymentDetails().setRazorpayPaymentLinkId(checkoutRequest.getRazorpayPaymentLinkId());
           order.getPaymentDetails().setRazorpayPaymentLinkReferenceId(checkoutRequest.getRazorpayPaymentLinkReferenceId());
           order.getPaymentDetails().setRazorpayPaymentLinkStatus(checkoutRequest.getRazorpayPaymentLinkStatus());
           order.getPaymentDetails().setStatus(paymentSuccess);
           order.setOrderStatus("processing");
           List<OrderItem> orderitems=order.getOrderItems();
           for(OrderItem o:orderitems) {
        	   
        	   o.setDeliveryDate(checkoutRequest.getDeliveryDate());
        	   orderItemRepository.save(o);
           }
           
           
          Order savedOrder= orderRepository.save(order);
          
          Map<String,Object> response = new HashMap<>();
          response.put("Order", savedOrder);

          response.put("status", true);
          response.put("message", "order checkout successfully");
          
           return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
        } else {
        	Map<String,Object> response = new HashMap<>();
          

            response.put("status", false);
            response.put("message", "order checkout failed");
        	 return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
        }
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

