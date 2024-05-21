package com.techverse.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String fullName;
	 private String password;
	 private String mobileNumber;
	 private String aadharNumber;
	 private String email; 
	 private String address;
	 private String referralCode; // optional
	 private String aadharCardImg;
	 private Date createdDate;
	 private String profile="";
	  
	 private String deviceToken="";
	 
	 
	 private String role;
	 
	 @JsonIgnore
	 @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE) // Cascade deletion of RecentSearch if associated user is deleted
	 private List<RecentSearch> recentSearches;
	 
	 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	 @JsonIgnore
	    private List<Product> products = new ArrayList<>();

	 
	 
	 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<ShippingAddress> shippingAddresses = new ArrayList<>();

	    public List<ShippingAddress> getShippingAddresses() {
	        return shippingAddresses;
	    }

	    public void setShippingAddresses(List<ShippingAddress> shippingAddresses) {
	        this.shippingAddresses = shippingAddresses;
	    }
	 
	    
	    
	    
	 
	 public List<RecentSearch> getRecentSearches() {
		return recentSearches;
	}



	public void setRecentSearches(List<RecentSearch> recentSearches) {
		this.recentSearches = recentSearches;
	}



	public String getProfile() {
		return profile;
	}



	public void setProfile(String profile) {
		this.profile = profile;
	}



	public User() {
		 // TODO Auto-generated constructor stub
	 }	 
	 
	 

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getMobileNumber() {
		return mobileNumber;
	}


	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


	public String getAadharNumber() {
		return aadharNumber;
	}


	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getReferralCode() {
		return referralCode;
	}


	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}


	public String getAadharCardImg() {
		return aadharCardImg;
	}


	public void setAadharCardImg(String aadharCardImg) {
		this.aadharCardImg = aadharCardImg;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public String getDeviceToken() {
		return deviceToken;
	}


	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}
	
	
	
	
	
	public List<Product> getProducts() {
		return products;
	}



	public void setProducts(List<Product> products) {
		this.products = products;
	}



	public User(String fullName, String phoneNumber, String aadharNumber, String email,
			String address, String referralCode, String aadharCardImg,String otp) {
		super();
		this.fullName = fullName;
		this.mobileNumber = phoneNumber;
		this.aadharNumber = aadharNumber;
		this.email = email;
		this.password=otp;
		 
		this.address = address;
		this.referralCode = referralCode;
		this.aadharCardImg = aadharCardImg;
		Instant instant = Instant.parse(Instant.now().toString());

	    
		 ZoneId zoneId = ZoneId.of("Asia/Kolkata"); // Choose the appropriate time zone
	     LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
	 
		this.createdDate = createdDate;
		this.role="Individual";
	}

	 
}
