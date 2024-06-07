package com.techverse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techverse.config.JwtProvider;
import com.techverse.exception.ProductException;
import com.techverse.exception.UserException;
import com.techverse.model.Product;
import com.techverse.model.User;
import com.techverse.repository.UserRepository;
import com.techverse.request.LoginRequest;
import com.techverse.response.UserSignUpResponse;
import com.techverse.service.ProductService;
import com.techverse.service.StorageService;
import com.techverse.service.UserService;

@RestController 
public class HomeController {

	   @Autowired
	    private ProductService productService;
	@Autowired
	StorageService storageService;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	UserRepository userRepository;  
	
    @Autowired
    private UserService userService;
    @GetMapping("/for")
	 public String print()
	 {
		 return "welcome";
	 }
	
	@GetMapping("/api/user/getbytoken")
	public Map<String, Object> getuser(@RequestHeader("Authorization") String authorizationHeader) throws UserException
	{ 
		 Optional<User> user=userService.findUserProfileByJwt(authorizationHeader);

		 Map<String,Object> response = new HashMap<>();
		 if(user.isPresent()) {
			 response.put("user", user);

		        response.put("status", true); 
		        return response;
		 }
		 else
		 {
			 response.put("status", false); 
			 response.put("message","user not found");
		     return response;
		 }
	       
 	
	}
	
	 @PutMapping("/api/user/update")
	    public Map<String, Object> updateUser(@RequestHeader("Authorization") String authorizationHeader,  
		 @RequestPart(value="fullName",required=false) String fullName,
			@RequestPart(value="phoneNumber",required=false) String phoneNumber,
			@RequestPart(value="email",required=false) String email,
			@RequestPart(value="address",required=false) String address,
	    @RequestPart(value="profile",required=false) MultipartFile profile)throws UserException
		 
	    { 
		 
		 User user=userService.findUserProfileByJwt(authorizationHeader).get();
		 user.setFullName(fullName);
		 user.setMobileNumber(phoneNumber);
		 user.setEmail(email);
		 user.setAddress(address);
		 if(profile!=null && !profile.isEmpty()) {
		user.setProfile(storageService.uploadFileOnAzure(profile));
		 }
			String mobileoremail= jwtProvider.getEmailfromToken(authorizationHeader);
			String token="";
			if(mobileoremail.matches("^\\d{10}$")) {
				token=jwtProvider.generateToken1(phoneNumber);
			}
			else {
				token=jwtProvider.generateToken1(email);
			}
		 
		 userRepository.save(user);
		 Map<String,Object> response = new HashMap<>();
	        response.put("updateduser", user);
	        response.put("token", token);
	        response.put("status", true);
	        response.put("message", "user updated successfully");
	        return response;
}
	 
	 
		@PostMapping("/api/user/favorite-products")
	    public Map<String, Object> addFavoriteProduct(@RequestHeader("Authorization") String jwt, @RequestBody Map<String, Long> request)throws UserException,ProductException {
			 Long productId = request.get("productId");
			User user =userService.findUserProfileByJwt(jwt).get();
			
			userService.addFavoriteProduct(user.getId(), productId);
			
			Map<String,Object> response = new HashMap<>();
	         
	        
	        response.put("status", true);
	        response.put("message", "product added to wishlist successfully");
	        return response;
			
	      
	    }
		
		@DeleteMapping("/api/user/favorite-products")
	    public User deleteFavoriteProduct(@RequestHeader("Authorization") String jwt,  @RequestParam Long productId) throws UserException, ProductException {
	        User user = userService.findUserProfileByJwt(jwt).get();
	        return userService.deleteFavoriteProduct(user.getId(), productId);
	    }

	    @GetMapping("/api/user/favorite-products")
	    public Map<String,Object> getFavoriteProducts(@RequestHeader("Authorization") String jwt) throws UserException {
	        User user = userService.findUserProfileByJwt(jwt).get();
	        
	        Map<String,Object> response = new HashMap<>();
	        
	        List<Product> product=userService.getFavoriteProducts(user.getId());
	    	response.put("product", productService.setfavouriteStatus(user, product));
			response.put("status", true);
	        response.put("message", "product retrived Successfully");
	        
	        return response;

	         
	    }
}
