package com.techverse.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techverse.config.JwtProvider;
import com.techverse.exception.UserException;
import com.techverse.model.Cart;
import com.techverse.model.User;
import com.techverse.repository.UserRepository;
import com.techverse.request.LoginRequest;
import com.techverse.response.ApiResponse;
import com.techverse.response.UserSignUpResponse;
import com.techverse.service.CartService;
import com.techverse.service.CustomUserServiceImplementation;
import com.techverse.service.EmailService;
import com.techverse.service.OtpService;
import com.techverse.service.UserService;

 
@RestController
@RequestMapping("/auth")
public class AuthController {
 
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private UserService userService;
	@Autowired
	private CartService cartService;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private CustomUserServiceImplementation customUserService;
	
	@Autowired
	private OtpService otpService;
	
	
	
	 


	@PostMapping("/signup")
	public ResponseEntity<?> createUserHandler( @RequestPart("fullName") String fullName,
			@RequestPart("phoneNumber") String phoneNumber,
			@RequestPart("aadharNumber") String aadharNumber,
			@RequestPart("email") String email,
			@RequestPart("address") String address,
			@RequestPart(value="referralCode",required=false) String referralCode,
			@RequestPart("aadharCardImg") MultipartFile aadharCardImg)throws UserException,IOException
	{
		 
		
		Optional<User> isEmailExist=userRepository.findByEmailu(email);
		Optional<User> isphoneExist=userRepository.findByPhoneNumber(phoneNumber);
		if(isEmailExist.isPresent()||isphoneExist.isPresent()) {
			throw new UserException("Email or phone is Allready Used with Another Account");
		}
		ApiResponse response=new ApiResponse();
		 String otp=otpService.generateOtpAll(email);
		 if(otp.equals("error")) {
			    response.setStatus(false);
	            response.setMessage("error to send otp ");
	            response.setJwt("");
	            
	              return new ResponseEntity<>(response, HttpStatus.OK);             
		 }
		 UserSignUpResponse userresponse=new UserSignUpResponse();
		User createdUser=userService.createUser(fullName, phoneNumber, aadharNumber, email, address, referralCode, aadharCardImg,passwordEncoder.encode(otp));
		 
		User savedUser=userRepository.save(createdUser);
		Cart cart= cartService.createCart(savedUser);
		 
		  userresponse.setStatus(true);
          userresponse.setMessage("signup success and otp send to your email id => "+otp);
          userresponse.setUser(savedUser);
		
		return new ResponseEntity<>(userresponse,HttpStatus.OK);
		
		
		
		
	}
	@PostMapping("/validateotp")
	public ResponseEntity<UserSignUpResponse> validateUserHandler(@RequestBody LoginRequest loginRequest)throws UserException
	{
		String mobileoremail= loginRequest.getMobileoremail();
		String otp=loginRequest.getOtp();
		 
		
		Authentication authentication=authenticate(mobileoremail,otp);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token= jwtProvider.generateToken(authentication);
		
		Optional<User> user=userRepository.findByPhoneNumberOrEmail(mobileoremail,mobileoremail);
		 UserSignUpResponse userresponse=new UserSignUpResponse();
		 userresponse.setStatus(true);
         userresponse.setMessage("verification Successful ");
         userresponse.setUser(user.get());
         userresponse.setToken(token);
		
		 
		
		return new ResponseEntity<UserSignUpResponse>(userresponse,HttpStatus.OK);
		
		
		
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<UserSignUpResponse> loginUserHandler(@RequestBody LoginRequest loginRequest)throws UserException
	{
		String mobileoremail= loginRequest.getMobileoremail();
		String otp=loginRequest.getOtp();
		 
		
		Authentication authentication=authenticate(mobileoremail,otp);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token= jwtProvider.generateToken(authentication);
		
		Optional<User> user=userRepository.findByPhoneNumberOrEmail(mobileoremail,mobileoremail);
		 
		 UserSignUpResponse userresponse=new UserSignUpResponse();
			
		 userresponse.setStatus(true);
         userresponse.setMessage("verification Successful "+otp);
         userresponse.setUser(user.get());
         userresponse.setToken(token);
		
		 
		
		return new ResponseEntity<UserSignUpResponse>(userresponse,HttpStatus.OK);
	 		
	}
	  /****final****/
    @GetMapping("/generateotp")
    public ResponseEntity<ApiResponse> generateotphandler(@RequestParam String mobileoremail)throws UserException {
   	  	String role="";
    	
   	
        ApiResponse response=new ApiResponse();
        response.setJwt("");

   
        	Optional<User> isEmailExist=userRepository.findByPhoneNumberOrEmail(mobileoremail,mobileoremail);
    		System.out.println("kfhgjkhfdghkfjh"+isEmailExist.isPresent());
    		if(!isEmailExist.isPresent()) { 
    			throw new UsernameNotFoundException("Email or phone is not registered please registered first");
    		}
    		 
    		 String otp=otpService.generateOtpAll(mobileoremail);
    		 if(otp.equals("error")) {
    			    response.setStatus(false);
    	            response.setMessage("error to send otp");
    	            response.setJwt("");
    	            
    	              return new ResponseEntity<>(response, HttpStatus.OK);             
    		 }
    		 response.setStatus(true);
             response.setMessage("OTP sent successfully."+otp);
             return ResponseEntity.ok(response);
    		 
    		 
         
    } 
    
  
    
    
    @PostMapping("/send-whatsapp")
    public void sendWhatsAppMessage(@org.springframework.web.bind.annotation.RequestBody Map<String, String> request) {
	      String phoneNumber = request.get("phoneNumber");
	    String message = request.get("message");
        otpService.sendWhatsAppMessage(phoneNumber, message);
    }

    /*
	  
    @PostMapping("/validateOtp")
	public ResponseEntity<ApiResponse> validate(@org.springframework.web.bind.annotation.RequestBody Map<String, String> request) {
    	ApiResponse response = new ApiResponse();
	    response.setJwt("");
	    String emailorphone = request.get("emailorphone");
	    String otp = request.get("otp");

	    if (StringUtils.isEmpty( emailorphone) || StringUtils.isEmpty(otp)) {
	    	 response.setStatus(false);
	        response.setMessage("Missing required credentials.");
	        return ResponseEntity.badRequest().body(response);
	    }

	    int otpVerificationResult = otpService.verifyOtp(emailorphone, otp);

	    if (otpVerificationResult == OtpVerificationResult.SUCCESS) {
 
	    	
	    	Authentication authentication=authenticate(emailorphone, otp);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			String token= jwtProvider.generateToken(authentication);
			
			response.setJwt(token);
	        response.setStatus(true);
	        response.setMessage("verification successful");
	      //  response.setData(token);
	        return ResponseEntity.ok(response);
	    } else if (otpVerificationResult == OtpVerificationResult.EXPIRED) {
	    	 response.setStatus(false);
	        response.setMessage("OTP has expired. Please request a new OTP.");
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	    } else {
	    	 response.setStatus(false);
	        response.setMessage("Invalid OTP. Please enter a valid OTP.");
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	    }
	}
*/
	private Authentication authenticate(String username, String password) {
		
		UserDetails userDetails=customUserService.loadUserByUsername(username);
		
		if(userDetails==null)
		{
			throw new BadCredentialsException("Invalid Username....");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword()))
		{
			throw new BadCredentialsException("Invalid OTP");
		}
		 
		return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
	}

}
