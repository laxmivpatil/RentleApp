package com.techverse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techverse.exception.ProductException;
import com.techverse.model.Product;
import com.techverse.model.User;
import com.techverse.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private StorageService storageService;
    


    public Product addProduct(MultipartFile p1, MultipartFile p2,  MultipartFile p3, MultipartFile p4, MultipartFile p5,
                              String title, String serialNo, String description, String category, String subcategory,
                              String houseNumber, String streetNumber, String address, String pincode,
                              double refundableSecurityDeposit, boolean daily, boolean monthly, boolean yearly,
                              Long dailyPrice, Long monthlyPrice, Long yearlyPrice,
                              int quantity, String availableFrom,
                              double weight, double height, double width, double depth,
                              User user) {
        Product product = new Product();
        // Set all the product details 
        if(p1 != null && !p1.isEmpty()) {
        	String imagePath1=storageService.uploadFileOnAzure(p1);
        	product.setProductImage1(imagePath1);
        	 
        	product.addImagePath(imagePath1);
        	
        }
        
        
        if(p2 != null && !p2.isEmpty()) {
        	String imagePath2=storageService.uploadFileOnAzure(p2);
        	product.setProductImage2(imagePath2);
        	product.addImagePath(imagePath2);
        	
        }
        
        if(p3 != null && !p3.isEmpty()) {
        	String imagePath3=storageService.uploadFileOnAzure(p3);
        	product.setProductImage3(imagePath3);
        	product.addImagePath(imagePath3);
        	
        }
        if(p4 != null && !p4.isEmpty()) {
        String imagePath4=storageService.uploadFileOnAzure(p4);
    	product.setProductImage4(imagePath4);
    	product.addImagePath(imagePath4);
        	
        }
        if(p5 != null && !p5.isEmpty()) {
        	String imagePath5=storageService.uploadFileOnAzure(p5);
        	product.setProductImage5(imagePath5);
        	product.addImagePath(imagePath5);
        }
        
        product.setTitle(title);
        product.setSerialNo(serialNo);
        product.setDescription(description);
        product.setCategory(category);
        product.setSubcategory(subcategory);
        product.setHouseNumber(houseNumber);
        product.setStreetNumber(streetNumber);
        product.setAddress(address);
        product.setPincode(pincode);
        product.setRefundableSecurityDeposit(refundableSecurityDeposit);
        product.setDaily(daily);
        product.setMonthly(monthly);
        product.setYearly(yearly);
        product.setDailyPrice(dailyPrice);
        product.setMoonthlyPrice(monthlyPrice);
        product.setYearlyPrice(yearlyPrice);
        product.setQuantity(quantity);
        product.setAvailableFrom(availableFrom);
        product.setWeight(weight);
        product.setHeight(height);
        product.setWidth(width);
        product.setDepth(depth);
        product.setUser(user);
        product.setActive(true);
        product.setAverageRating(0);
        product.setCreatedAt(LocalDateTime.now());;

        return productRepository.save(product);
    }
    public List<Product> getProductsByUserId(Long userId) {
        return productRepository.findByUserId(userId);
    }
    
    public List<Product> getAllActiveProductsOfOtherUsers(Long userId) {
    	 
    	// List<Product> products = productRepository.findAllByUserIdNotAndActiveTrue(userId);
    	 List<Product> products = productRepository.findAllByActiveTrue();
    	    // Update the favorite status of the products
    	    return products;
         
    }
    public List<Product> getTop15PopularProducts() {
        return productRepository.findTop15PopularProducts();
    }
    public Product findProductById(Long id) throws ProductException {
		// TODO Auto-generated method stub
		Optional<Product> product=productRepository.findById(id);
		if(product.isPresent())
		{
			return product.get();
		}
		throw new ProductException("Product Not Found with id "+id);
	}
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Product getProductDetailsWithUser(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            // Fetch user details associated with the product
            
            return product;
        }
        return null;
    }
    
    public List<Product> searchByTitleOrCategory(String searchText) {
        // Assuming your ProductRepository has a method for searching by title or category
        return productRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(searchText, searchText);
    }
    
    public List<Product> findProductsByCategories(Set<String> categories) {
        // Implement the logic to find products by categories
        return productRepository.findByCategoryIn(categories);
    }
    
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public boolean changeProductStatus(Optional<Product> optionalProduct) {
        
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setActive(!product.isActive()); // Toggle status (active to inactive, inactive to active)
            productRepository.save(product);
            return true;
        }
        return false;
    }
    
    
    public List<Product> setfavouriteStatus(User user,List<Product> products)
    {
    	 
    	Set<Long> favoriteProductIds = user.getFavoriteProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toSet());
    	products.forEach(product -> product.setFavorite(favoriteProductIds.contains(product.getId())));
    	return products;

    }
    
    public Product setfavouriteStatussingleproduct(User user,Product p)
    {
    	if( user.getFavoriteProducts().contains(p))
    	{
    		p.setFavorite(true);
    	}
    	return p;
    }
    
    public List<Product> getProductsInSameCategory(List<Product> products) {
        if (products.isEmpty()) {
            return Collections.emptyList();
        }

        // Get unique categories from the recent search products
        Set<String> categories = products.stream()
                                         .map(Product::getCategory)
                                         .collect(Collectors.toSet());

        // Get product IDs to exclude them from the search
        List<Long> productIds = products.stream()
                                        .map(Product::getId)
                                        .collect(Collectors.toList());

        List<Product> similarProducts = new ArrayList<>();

        // Find products in the same categories, excluding the recent search products
        for (String category : categories) {
            similarProducts.addAll(productRepository.findByCategoryAndIdNotIn(category, productIds));
        }

        return similarProducts;
    }
    
    
}
