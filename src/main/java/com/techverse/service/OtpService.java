package com.techverse.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.techverse.model.OtpEntity;
import com.techverse.model.OtpVerificationResult;
import com.techverse.repository.OtpRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class OtpService {
	 @Autowired
	    private EmailService emailService;
	 @Autowired
	    private OtpRepository otpRepository;
	 @Autowired
		private BCryptPasswordEncoder passwordEncoder;
	    
    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

    @Value("${twilio.phone-number}")
    private String twilioWhatsappNumber;
   
    public String generateOtp() {
        // Generate a 6-digit random OTP
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
    }
 //    please remove this comment to send sms on mobile and comment above simple sendotp method
    public boolean sendOtp(String phoneNumber, String otp) {
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);
        String messageBody = "Your OTP is: " + otp;
        String  phoneNumber1 = "+91" + phoneNumber;

             System.out.println("OTP sent successfully! SID: " +otp);
                OtpEntity otpEntity = new OtpEntity(phoneNumber,otp, expiryTime);
                otpRepository.save(otpEntity);
                return true;
           
         
    }
    
    public String generateOtpAll(String mobileoremail) {
   	  	String role="";
    	System.out.println();
   	
        
        try {
            if (mobileoremail != null && !mobileoremail.isEmpty()) {
            	 String otp = generateOtp();
            	 String encodedotp =passwordEncoder.encode(otp);
            	if(mobileoremail.matches("^\\d{10}$")) {
            		if (sendOtp(mobileoremail, encodedotp)) {
                    	return otp;
                    } 
            		else {
                       return "error";
                    }
            	}
            	else {
            		if (emailService.sendEmail(mobileoremail, encodedotp)) {
                    	 return otp;
                    } else {
                       return "error";
                    }
            	}
                    
                 
            } else {
                return "error";
            }
        } catch (Exception e) {
        	System.out.println(e);
            return "error";
        }
    } 
    
    
 
 /*   public boolean sendOtp(String phoneNumber, String otp) {
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);
        String messageBody = "Your OTP is: " + otp;
        Twilio.init(accountSid, authToken);
       String  phoneNumber1 = "+91" + phoneNumber;

        try {
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(phoneNumber1),
                    new com.twilio.type.PhoneNumber(twilioPhoneNumber),
                    messageBody)
                    .create();

            // Check if the message was sent successfully
            
            if (message.getSid()!=null) {
                System.out.println("OTP sent successfully! SID: " +  passwordEncoder.encode(otp));
                OtpEntity otpEntity = new OtpEntity(phoneNumber,  passwordEncoder.encode(otp), expiryTime);
                otpRepository.save(otpEntity);
                return true;
            } else {
                System.out.println("Failed to send OTP!");
                return false;
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., TwilioException)
            System.out.println("Error occurred while sending OTP: " + e.getMessage());
            return false;
        }
    }
    */
    public int verifyOtp(String phoneNumber, String otp) {
        Optional<OtpEntity> otpEntityOptional = otpRepository.findByPhoneNumber(phoneNumber);

        if (otpEntityOptional.isPresent() &&  otp.equals(otpEntityOptional.get().getOtp())) {
            OtpEntity otpEntity = otpEntityOptional.get();
            if (otpEntity.getExpiryTime().isAfter(LocalDateTime.now())) {
                // OTP is valid and not expired
                // Optionally, you can remove the used OTP from the database to prevent replay attacks.
                otpRepository.delete(otpEntity);
                return OtpVerificationResult.SUCCESS;
            } else {
                // OTP has expired
                return OtpVerificationResult.EXPIRED;
            }
        }

        // OTP verification failed
        return OtpVerificationResult.INVALID;
    }     
    
    
     public boolean sendmessagetosubadmin(String phoneNumber) {
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);
        String url="localhost:8080/subadmin/login?"+phoneNumber;
        String messageBody = "Welcome Subadmin to satya app please click on "+url +" to login " ;
        Twilio.init(accountSid, authToken);
       String  phoneNumber1 = "+91" + phoneNumber;

        try {
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(phoneNumber1),
                    new com.twilio.type.PhoneNumber(twilioPhoneNumber),
                    messageBody)
                    .create();

            // Check if the message was sent successfully
            
            if (message.getSid()!=null) {
                System.out.println("message send Successfully " + message);
                return true;
            } else {
                System.out.println("Failed to send message");
                return false;
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., TwilioException)
            System.out.println("Error occurred while sending message " + e.getMessage());
            return false;
        }
    } 
     public void sendWhatsAppMessage(String toPhoneNumber, String message) {
         Twilio.init(accountSid, authToken);
         String  tophoneNumber1 = "+91" + toPhoneNumber;

         System.out.println("hisssss"+toPhoneNumber+"  "+message);
         Message.creator(
                 new com.twilio.type.PhoneNumber("whatsapp:" + tophoneNumber1),
                 new com.twilio.type.PhoneNumber("whatsapp:" + twilioWhatsappNumber),
                 message
         ).create();
     }
      
}
