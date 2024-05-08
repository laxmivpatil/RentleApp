package com.techverse.model;
 

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String serialNo;
    private String description;
     
    private boolean active=true;
    private String category;

    
    private String subcategory;

    private String productImage1="";
    private String productImage2="";
    private String productImage3="";
    private String productImage4="";
    private String productImage5="";
    
    private String houseNumber;
    private String streetNumber;
    private String address;
    private String pincode;

    private double refundableSecurityDeposit;

     
    private boolean daily=false;
    private boolean monthly=false;
    private boolean yearly=false;
    
    private Long dailyPrice=0L;
    private Long moonthlyPrice=0L;
    private Long yearlyPrice=0L;
    
    private int quantity;
    private String availableFrom;

    private double weight;
    private double height;
    private double width;
    private double depth;
    
    
   	private double averageRating;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

     
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<ImagePath> imagePaths = new ArrayList<>();
    
    
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE) // Cascade deletion of RecentSearch if associated product is deleted
    private List<RecentSearch> recentSearches;
    

    public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public List<ImagePath> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<ImagePath> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public void addImagePath(String path) {
        ImagePath imagePath = new ImagePath();
        imagePath.setPath(path);
        this.imagePaths.add(imagePath);
    }


	public List<RecentSearch> getRecentSearches() {
		return recentSearches;
	}

	public void setRecentSearches(List<RecentSearch> recentSearches) {
		this.recentSearches = recentSearches;
	}

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}

 	public boolean isActive() {
		return active;
	}



	public void setActive(boolean active) {
		this.active = active;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getSerialNo() {
		return serialNo;
	}



	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	 


	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public String getSubcategory() {
		return subcategory;
	}



	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}



	public String getProductImage1() {
		return productImage1;
	}



	public void setProductImage1(String productImage1) {
		this.productImage1 = productImage1;
	}



	public String getProductImage2() {
		return productImage2;
	}



	public void setProductImage2(String productImage2) {
		this.productImage2 = productImage2;
	}



	public String getProductImage3() {
		return productImage3;
	}



	public void setProductImage3(String productImage3) {
		this.productImage3 = productImage3;
	}



	public String getProductImage4() {
		return productImage4;
	}



	public void setProductImage4(String productImage4) {
		this.productImage4 = productImage4;
	}



	public String getProductImage5() {
		return productImage5;
	}



	public void setProductImage5(String productImage5) {
		this.productImage5 = productImage5;
	}



	public String getHouseNumber() {
		return houseNumber;
	}



	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}



	public String getStreetNumber() {
		return streetNumber;
	}



	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getPincode() {
		return pincode;
	}



	public void setPincode(String pincode) {
		this.pincode = pincode;
	}



	public double getRefundableSecurityDeposit() {
		return refundableSecurityDeposit;
	}



	public void setRefundableSecurityDeposit(double refundableSecurityDeposit) {
		this.refundableSecurityDeposit = refundableSecurityDeposit;
	}



	public boolean isDaily() {
		return daily;
	}



	public void setDaily(boolean daily) {
		this.daily = daily;
	}



	public boolean isMonthly() {
		return monthly;
	}



	public void setMonthly(boolean monthly) {
		this.monthly = monthly;
	}



	public boolean isYearly() {
		return yearly;
	}



	public void setYearly(boolean yearly) {
		this.yearly = yearly;
	}



	public Long getDailyPrice() {
		return dailyPrice;
	}



	public void setDailyPrice(Long dailyPrice) {
		this.dailyPrice = dailyPrice;
	}



	public Long getMoonthlyPrice() {
		return moonthlyPrice;
	}



	public void setMoonthlyPrice(Long moonthlyPrice) {
		this.moonthlyPrice = moonthlyPrice;
	}



	public Long getYearlyPrice() {
		return yearlyPrice;
	}



	public void setYearlyPrice(Long yearlyPrice) {
		this.yearlyPrice = yearlyPrice;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	public String getAvailableFrom() {
		return availableFrom;
	}



	public void setAvailableFrom(String availableFrom) {
		this.availableFrom = availableFrom;
	}



	public double getWeight() {
		return weight;
	}



	public void setWeight(double weight) {
		this.weight = weight;
	}



	public double getHeight() {
		return height;
	}



	public void setHeight(double height) {
		this.height = height;
	}



	public double getWidth() {
		return width;
	}



	public void setWidth(double width) {
		this.width = width;
	}



	public double getDepth() {
		return depth;
	}



	public void setDepth(double depth) {
		this.depth = depth;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

    
    
    
    
    
    
    // getters and setters
}






 
    
    
    

