package com.techverse.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techverse.config.JwtProvider;
import com.techverse.exception.UserException;
import com.techverse.model.User;
import com.techverse.repository.UserRepository;
import com.techverse.request.LoginRequest;
import com.techverse.response.UserSignUpResponse;
import com.techverse.service.StorageService;
import com.techverse.service.UserService;

@RestController 
public class HomeController {

	@Autowired
	StorageService storageService;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	UserRepository userRepository;  
	
    @Autowired
    private UserService userService;
	
	
	@GetMapping("/api/user/getbytoken")
	public Map<String, Object> getuser(@RequestHeader("Authorization") String authorizationHeader) throws UserException
	{ 
		 User user=userService.findUserProfileByJwt(authorizationHeader);
		 Map<String,Object> response = new HashMap<>();
	        response.put("user", user);

	        response.put("status", true); 
	        return response;
 	
	}
	
	 @PutMapping("/api/user/update")
	    public Map<String, Object> updateUser(@RequestHeader("Authorization") String authorizationHeader,  
		 @RequestPart(value="fullName",required=false) String fullName,
			@RequestPart(value="phoneNumber",required=false) String phoneNumber,
			@RequestPart(value="email",required=false) String email,
			@RequestPart(value="address",required=false) String address,
	    @RequestPart(value="profile",required=false) MultipartFile profile)throws UserException
		 
	    { 
		 
		 User user=userService.findUserProfileByJwt(authorizationHeader);
		 	 user.setFullName(fullName);
		 
		  
			 user.setMobileNumber(phoneNumber);
		 
		  
			 user.setEmail(email);
		  
			 user.setAddress(address);
			 if(profile!=null && !profile.isEmpty()) {
			user.setProfile(storageService.uploadFileOnAzure(profile));
			 }
			 
			 
		 
		 userRepository.save(user);
		 Map<String,Object> response = new HashMap<>();
	        response.put("updateduser", user);

	        response.put("status", true);
	        response.put("message", "user updated successfully");
	        return response;
}
}
