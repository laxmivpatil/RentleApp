package com.techverse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techverse.exception.CartItemException;
import com.techverse.exception.UserException;
import com.techverse.model.CartItem;
import com.techverse.model.User;
import com.techverse.response.ApiResponse;
import com.techverse.service.CartItemService;
import com.techverse.service.UserService;

@RestController
@RequestMapping("api/cart_items")
public class CartItemController {
	
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private UserService userService;
	
	
	@DeleteMapping("/{cartItemId}")
	public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String jwt)throws UserException,CartItemException{
				
				
		User user=userService.findUserProfileByJwt(jwt).get();
		System.out.println(user.getId());
				cartItemService.removeCartItem(user, cartItemId);
				 ApiResponse res=new ApiResponse();
				 res.setMessage("Item deleted from cart successfully");
				 res.setStatus(true);
				 return new ResponseEntity<>(res,HttpStatus.OK);
				
			}
	@PutMapping("/cart/{cartItemId}")
	public ResponseEntity<CartItem> updateCartItem(@PathVariable Long cartItemId,
			@RequestBody CartItem cartItem,
			@RequestHeader("Authorization") String jwt)throws UserException,CartItemException{
				
				
		User user=userService.findUserProfileByJwt(jwt).get();
				CartItem updatedCartItem=cartItemService.updateCartItem(user.getId(),cartItemId, cartItem);
				 
				 return new ResponseEntity<>(updatedCartItem,HttpStatus.OK);
				
			}
	
	

}