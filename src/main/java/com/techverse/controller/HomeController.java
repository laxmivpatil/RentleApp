package com.techverse.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techverse.exception.UserException;
import com.techverse.model.User;
import com.techverse.request.LoginRequest;
import com.techverse.response.UserSignUpResponse;
import com.techverse.service.StorageService;

@RestController 
public class HomeController {

	@Autowired
	StorageService storageService;
	
	
	@GetMapping("/")
	public String home(@RequestPart("aadharCardImg") MultipartFile aadharCardImg)
	{ 
		 String path=storageService.uploadFileOnAzure(aadharCardImg);
		 
		return "Welcome "+path;
		
 	
	}
}
