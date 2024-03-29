package com.techverse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techverse.model.User; 

public interface UserRepository extends JpaRepository<User,Long> {
	
	public User findByEmail(String email);
	
	 
	 
		
	 @Query("SELECT u FROM User u WHERE u.mobileNumber = :phoneNumber OR u.email = :email")
	    Optional<User> findByPhoneNumberOrEmail(@Param("email") String  Email,@Param("phoneNumber") String phoneNumber);

}
