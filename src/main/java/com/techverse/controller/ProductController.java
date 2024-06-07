package com.techverse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

 
import com.techverse.exception.UserException;
import com.techverse.model.Category;
import com.techverse.model.Product;
import com.techverse.model.RecentSearch;
import com.techverse.model.User;
import com.techverse.repository.ProductRepository;
import com.techverse.service.ProductService;
import com.techverse.service.RecentSearchService;
import com.techverse.service.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private RecentSearchService recentSearchService;
  
    
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
    	
    	User user=userService.findUserProfileByJwt(authorizationHeader).get();
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
    
    
  /*  @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    */
    @GetMapping("/popular")
    public Map<String, Object> getTop15PopularProducts(@RequestHeader("Authorization") String authorizationHeader)throws UserException  {
        List<Product> topProducts = productService.getTop15PopularProducts();
        User user=userService.findUserProfileByJwt(authorizationHeader).get();
        Map<String,Object> response = new HashMap<>();
        
        
        
        response.put("product", productService.setfavouriteStatus(user, topProducts));

        response.put("status", true);
        response.put("message", "product retrived Successfully");
        return response;
        
    }
    @GetMapping("/all")
    public Map<String, Object> getAllActiveProductsOfOtherUsers(@RequestHeader("Authorization") String authorizationHeader)  throws UserException  {
    	User user=userService.findUserProfileByJwt(authorizationHeader).get();
    	 
    	
    	List<Product> products=productService.getAllActiveProductsOfOtherUsers(user.getId());
    	
    	Map<String,Object> response = new HashMap<>();
        response.put("product", productService.setfavouriteStatus(user, products));

        response.put("status", true);
        response.put("message", "product retrived Successfully");
        return response;
    }
    
    @GetMapping("/byproductid/{productId}")
    public Map<String, Object> getProductDetailsWithUser(@PathVariable Long productId,@RequestHeader("Authorization") String authorizationHeader) throws UserException {
    	User user=userService.findUserProfileByJwt(authorizationHeader).get();
    	System.out.println(user.getId());
    	Product p=productService.getProductDetailsWithUser(productId);
        
    	if (user != null && p != null) {
            // Save recent search for the logged-in user
            saveRecentSearch(p, user);
        }
    	if(user.getFavoriteProducts().contains(p))
    	{
    		p.setFavorite(true);
    	}
        Map<String,Object> response = new HashMap<>();
        response.put("product", p);
        response.put("user", p.getUser());

        response.put("status", true);
        response.put("message", "product retrived Successfully");
        return response;
    }
    public void saveRecentSearch(Product product, User user) {
        RecentSearch recentSearch = new RecentSearch();
        recentSearch.setProduct(product);
        recentSearch.setUser(user);
        recentSearch.setSearchTimestamp(LocalDateTime.now());
        // Check and manage recent search count for the user
        manageRecentSearchCount(user, recentSearch);

        recentSearchService.save(recentSearch);
    }

    private void manageRecentSearchCount(User user, RecentSearch recentSearch) {
        int maxRecentSearches = 15;
        List<RecentSearch> userRecentSearches = recentSearchService.findRecentSearchesByUser(user);

        if (userRecentSearches.size() >= maxRecentSearches) {
            // Remove the oldest search (first in the list)
            RecentSearch oldestSearch = userRecentSearches.get(0);
            recentSearchService.delete(oldestSearch);
        }
    }
    @GetMapping("/byuserid")
    public Map<String, Object> getProductsByUserId(@RequestHeader("Authorization") String authorizationHeader)  throws UserException {
    	User user=userService.findUserProfileByJwt(authorizationHeader).get();
    	List<Product> products=productService.getProductsByUserId(user.getId());
        Map<String,Object> response = new HashMap<>();
        response.put("product", productService.setfavouriteStatus(user, products) );

        response.put("status", true);
        response.put("message", "product retrived Successfully");
        return response;
        
        
        
    }
    @GetMapping("/homeproduct")
    public Map<String, Object> getProductsforhome(@RequestHeader("Authorization") String authorizationHeader)  throws UserException {
    	User user=userService.findUserProfileByJwt(authorizationHeader).get();
    	List<Product> products=productRepository.findTop5ByActiveOrderByCreatedAtDesc(true);
        Map<String,Object> response = new HashMap<>();
        response.put("product", productService.setfavouriteStatus(user, products));

        response.put("status", true);
        response.put("message", "product retrived Successfully");
        return response;
        
        
        
    }
    @GetMapping("/search/{searchText}")
    public Map<String, Object>  searchProducts(@PathVariable(required = false) String searchText,@RequestHeader("Authorization") String authorizationHeader) throws UserException {
    	User user=userService.findUserProfileByJwt(authorizationHeader).get();
    	
    	Map<String, Object> response = new HashMap<>();
    
    	  if (searchText != null && !searchText.isEmpty()) {
              List<Product> products = productService.searchByTitleOrCategory(searchText);
              response.put("product", productService.setfavouriteStatus(user, products));
              response.put("status", true);
              response.put("message", "Products retrieved successfully");
          } else {
              response.put("product", Collections.emptyList());
              response.put("status", true);
              response.put("message", "No search text provided, returning empty list of products");
          }

          return response;
    }
    @GetMapping("/search/")
    public Map<String, Object>  searchProducts() {
    	  Map<String, Object> response = new HashMap<>();
    
    	  
              response.put("product", Collections.emptyList());
              response.put("status", true);
              response.put("message", "No search text provided, returning empty list of products");
         

          return response;
    }
    @DeleteMapping("/delete/{productId}")
    public Map<String, Object> deleteProduct(@RequestHeader("Authorization") String authorizationHeader,    		
    		@PathVariable Long productId ) throws UserException  {
    	
    	
    System.out.println("hiiii"+authorizationHeader);
    	
    	User user=userService.findUserProfileByJwt(authorizationHeader).get();

    	Map<String,Object> response = new HashMap<>();
    	 boolean deleted = productService.deleteProduct(productId);
    	    if (deleted) {
    	    	response.put("status", true);
    	        response.put("message", "product deleted Successfully");
    	         
    	    } else {
    	    	response.put("status", false);
    	        response.put("message", "Product not found");
    	         
    	    }
    	    System.out.println(user.getEmail());
    	 
        
        return response;
        
        
   
                 
    }
    
    
    @PutMapping("/status/{id}")
    public Map<String, Object> changeProductStatus(@RequestHeader("Authorization") String authorizationHeader,@PathVariable Long id)throws UserException   {
        User user=userService.findUserProfileByJwt(authorizationHeader).get();

    	Map<String,Object> response = new HashMap<>();
    	Optional<Product> optionalProduct = productRepository.findById(id);
    	System.out.println(optionalProduct.get().isActive());
    	 boolean statusChanged = productService.changeProductStatus(optionalProduct);
    	 System.out.println(optionalProduct.get().isActive());
         
             if ( statusChanged ) {
    	    	response.put("status", true);
    	    	response.put("activestatus", optionalProduct.get().isActive());
    	        response.put("message", "Product status changed successfully");
    	         
    	    } else {
    	    	response.put("status", false);
    	        response.put("message", "Product not found");
    	         
    	    }
    	
    	
    	
    	System.out.println(user.getEmail());
    	 
        
        return response;
        
        
        
         
    }
}
