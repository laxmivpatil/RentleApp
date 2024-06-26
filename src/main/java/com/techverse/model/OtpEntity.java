package com.techverse.model;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
public class OtpEntity {

    @Id
    @Column(name = "phone_number")
    private String phoneNumber;

    private String otp;

    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public LocalDateTime getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(LocalDateTime expiryTime) {
		this.expiryTime = expiryTime;
	}

	public OtpEntity(String phoneNumber, String otp, LocalDateTime expiryTime) {
		super();
		this.phoneNumber = phoneNumber;
		this.otp = otp;
		this.expiryTime = expiryTime;
	}

	public OtpEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	

    
    
    
    // Constructors, getters, and setters
}
