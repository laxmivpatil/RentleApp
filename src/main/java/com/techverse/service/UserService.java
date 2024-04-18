package com.techverse.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.techverse.exception.UserException;
import com.techverse.model.User;

public interface UserService {
	
	public User findUserById(Long userId)throws UserException;
	
	public Optional<User> findUserProfileByJwt(String jwt)throws UserException;
	
	public User createUser( String fullName, String phoneNumber, String aadharNumber, String email,
			String address, String referralCode, MultipartFile aadharCardImg,String otp)throws IOException;

}
