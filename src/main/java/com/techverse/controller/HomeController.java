package com.techverse.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techverse.exception.UserException;
import com.techverse.model.User;
import com.techverse.request.LoginRequest;
import com.techverse.response.UserSignUpResponse;

@RestController 
public class HomeController {

	
	@GetMapping("/")
	public String home()
	{ 
		return "Welcome";
		
 	
	}
}
