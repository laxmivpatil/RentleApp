package com.techverse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.exception.ProductException;
import com.techverse.model.Cart;
import com.techverse.model.CartItem;
import com.techverse.model.Product;
import com.techverse.model.User;
import com.techverse.repository.CartRepository;
import com.techverse.request.AddItemRequest;
 

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ProductService productService;  
	
	
	 
	public Cart createCart(User user) {   
		
		Cart cart=new Cart();
		cart.setUser(user);
		
		
		return cartRepository.save(cart);
	}

	 
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		Cart cart=cartRepository.findByUserId(userId);
		
		Product product=productService.findProductById(req.getProductId());
		
		CartItem isPresent=cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
		
		if(isPresent==null) {
			CartItem cartItem=new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);
			
			Long  price=req.getQuantity()*product.getDailyPrice();
			cartItem.setPrice(price);
			cartItem.setSize("");
			
			CartItem createdCartItem=cartItemService.createCartitem(cartItem);
			cart.getCartItems().add(createdCartItem);
			
		}
		
		
		return "Item Add To Cart";
	}

 
	public Cart findUserCart(Long userId) { 
		
		Cart cart=cartRepository.findByUserId(userId);
		
		Long totalPrice=0L;
		Long totalDiscountedPrice=0L;
		int totalItem=0;
		
		for(CartItem cartItem:cart.getCartItems()) {
			totalPrice =totalPrice+cartItem.getPrice();
			totalDiscountedPrice =0L;
			totalItem =totalItem+cartItem.getQuantity();
		}
		
		
		cart.setTotalDicountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscounte(totalPrice-totalDiscountedPrice);
		
		
		return cartRepository.save(cart);
	}

}
