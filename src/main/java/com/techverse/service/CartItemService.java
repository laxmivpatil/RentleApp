package com.techverse.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.exception.CartItemException;
import com.techverse.exception.UserException;
import com.techverse.model.Cart;
import com.techverse.model.CartItem;
import com.techverse.model.Product;
import com.techverse.model.User;
import com.techverse.repository.CartItemRepository;
import com.techverse.repository.CartRepository;

@Service
public class CartItemService {
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartRepository cartRepository;
	public CartItem createCartitem(CartItem cartItem) {
		 
		cartItem.setQuantity(1);
		Product p=cartItem.getProduct();
		if(p.isDaily()) {
		cartItem.setPrice(cartItem.getProduct().getDailyPrice()*cartItem.getQuantity());
		}
		else if(p.isMonthly()) {
			cartItem.setPrice(cartItem.getProduct().getMoonthlyPrice()*cartItem.getQuantity());
			
		}
		else if(p.isYearly()) {
			cartItem.setPrice(cartItem.getProduct().getYearlyPrice()*cartItem.getQuantity());
			
		}
		cartItem.setDiscountedPrice(0L*cartItem.getQuantity());
		
		
		CartItem createdCartItem=cartItemRepository.save(cartItem);
		
		return createdCartItem;
		
		
		
	}

 
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
		 
		CartItem item=findCartItemById(id);
		User user=userService.findUserById(item.getUserId());
		
		if(user.getId().equals(userId)) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getDailyPrice());
			item.setDiscountedPrice(0L*item.getQuantity());
			
		}
		
		
		return cartItemRepository.save(item);
	}

	 
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
		 
		CartItem cartItem=cartItemRepository.isCartItemExist(cart, product, size, userId);
		
		
		
		return cartItem;
	}
 
	public void removeCartItem(User reqUser, Long cartItemId) throws CartItemException, UserException {
		
		CartItem cartItem=findCartItemById(cartItemId);
		
		User user=userService.findUserById(cartItem.getUserId());
		
System.out.println(cartItem.getUserId());
		
		if(user.getId().equals(reqUser.getId())) {
			cartItemRepository.deleteById(cartItemId);
		}
		else {
			throw new UserException("you cant remove another users item");
		}
		
	}

	 
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		Optional<CartItem> cartItem=cartItemRepository.findById(cartItemId);
		
		if(cartItem.isPresent()) {
			return cartItem.get();
		}
		
		throw new CartItemException("cart item not found with id "+cartItemId);
	}
	public void clearUserCart(Long userId) {
	    List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
	    
	     
	    cartItemRepository.deleteAll(cartItems);
	}

}
