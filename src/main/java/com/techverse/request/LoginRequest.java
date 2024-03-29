package com.techverse.request;

public class LoginRequest {
	
	
	
	private String mobileoremail;
	private String otp;
	
	
	
	public LoginRequest() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public LoginRequest(String mobileoremail, String otp) {
		super();
		this.mobileoremail = mobileoremail;
		this.otp = otp;
	}



	public String getMobileoremail() {
		return mobileoremail;
	}
	public void setMobileoremail(String mobileoremail) {
		this.mobileoremail = mobileoremail;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	 

}
