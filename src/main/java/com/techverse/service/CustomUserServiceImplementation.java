package com.techverse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techverse.exception.UserException;
import com.techverse.model.OtpEntity;
import com.techverse.model.User;
import com.techverse.repository.OtpRepository;
import com.techverse.repository.UserRepository; 

@Service
public class CustomUserServiceImplementation implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OtpRepository otpRepository;
	 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Optional<User> user=userRepository.findByPhoneNumberOrEmail(username, username);
		 Optional<OtpEntity> otpEntityOptional = otpRepository.findByPhoneNumber(username);
		 
		 if(!user.isPresent()) {
			 throw new UsernameNotFoundException("user not found with email- "+username);
		 }
		 List<GrantedAuthority> authorities=new ArrayList<>();
		return new org.springframework.security.core.userdetails.User(username,otpEntityOptional.get().getOtp(),authorities);
	}

	 
}
