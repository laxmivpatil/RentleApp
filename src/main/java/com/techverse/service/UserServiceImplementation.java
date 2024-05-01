package com.techverse.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techverse.config.JwtProvider;
import com.techverse.exception.UserException;
import com.techverse.model.User;
import com.techverse.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private StorageService storageService;
	 
	  
    @Autowired
    private JwtProvider jwtProvider; 

	@Override
	public User findUserById(Long userId) throws UserException {
		Optional<User> user=userRepository.findById(userId);
		if(user.isPresent())
			return user.get();
		throw new UserException("User Not Found with id  "+userId);
	}

	@Override
	public Optional<User> findUserProfileByJwt(String jwt) throws UserException {

		System.out.println("jkdhfjdshjg");
		String EmailorPhone=jwtProvider.getEmailfromToken(jwt);
		
		System.out.println(EmailorPhone);
		
		return userRepository.findByPhoneNumberOrEmail(EmailorPhone, EmailorPhone);
	}

	@Override
	public User createUser(String fullName, String phoneNumber, String aadharNumber, String email, String address,
			String referralCode, MultipartFile aadharCardImg,String otp) throws IOException{
		String path = "";
		// Add validation logic, password hashing, etc.

		if (aadharCardImg != null && !aadharCardImg.isEmpty()) {
			// Implement logic to handle profile photo upload, save to storage, etc.
			// For example, you can save the file to a specific directory or cloud storage.
			// Update admin's profile photo URL in the database accordingly.
			// admin.setProfilePhotoUrl(savedProfilePhotoUrl);
			System.out.println("hi "+aadharCardImg);
			//  path=storageService.uploadFileOnAzure(aadharCardImg);

		}
		User individual = new User(fullName, phoneNumber, aadharNumber, email, address, referralCode, path,otp);
		return userRepository.save(individual);

	}

}
