package com.techverse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.techverse.config.JwtProvider;
import com.techverse.exception.UserException;
import com.techverse.model.Category;
import com.techverse.model.Product;
import com.techverse.model.User;
import com.techverse.service.ProductService;
import com.techverse.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
  
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/add")
    public Map<String, Object> addProduct(@RequestHeader("Authorization") String authorizationHeader,    		
    		@RequestPart(required = false) String title,
    		@RequestPart(required = false) String serialNo,
    		@RequestPart(required = false) String description,
    		@RequestPart(required = false) String category,
    		@RequestPart(required = false) String subcategory,
    		@RequestPart(required = false) String houseNumber,
    		@RequestPart(required = false) String streetNumber,
    		@RequestPart(required = false) String address,
    		@RequestPart(required = false) String pincode,
    		@RequestPart(required = false) String refundableSecurityDeposit,
    		@RequestPart(required = false) String daily,
    		@RequestPart(required = false) String monthly,
    		@RequestPart(required = false) String yearly,
    		@RequestPart(required = false) String dailyPrice,
    		@RequestPart(required = false) String monthlyPrice,
    		@RequestPart(required = false) String yearlyPrice,
    		@RequestPart(required = false) String quantity,
    		@RequestPart(required = false) String availableFrom,
    		@RequestPart(required = false) String  weight,
    		@RequestPart(required = false) String  height,
    		@RequestPart(required = false) String  width,
    		@RequestPart(required = false) String  depth,
    		@RequestPart(name ="productImage1",required = false) MultipartFile productImage1,
    		@RequestPart(required = false) MultipartFile productImage2,
    		@RequestPart(required = false) MultipartFile productImage3,
    		@RequestPart(required = false) MultipartFile productImage4,
    		@RequestPart(required = false) MultipartFile productImage5) throws UserException  {
    	
    	
    System.out.println("hiiii"+authorizationHeader);
    	
    	User user=userService.findUserProfileByJwt(authorizationHeader);
    	System.out.println(user.getEmail());
    	
    	Product p= productService.addProduct(productImage1,productImage2,productImage3,productImage4,productImage5,
        		title, serialNo, description, category, subcategory,
                houseNumber, streetNumber, address, pincode, Double.parseDouble(refundableSecurityDeposit), Boolean.parseBoolean(daily),
                Boolean.parseBoolean(monthly),Boolean.parseBoolean(yearly), Long.parseLong(dailyPrice), Long.parseLong(monthlyPrice),Long.parseLong(yearlyPrice), Integer.parseInt( quantity), availableFrom,
                Double.parseDouble(weight), Double.parseDouble(height),Double.parseDouble(width), Double.parseDouble(depth), user);
    	Map<String,Object> response = new HashMap<>();
        response.put("product", p);

        response.put("status", true);
        response.put("message", "product added Successfully");
        return response;
        
        
   
                 
    }
    
     
    
}
