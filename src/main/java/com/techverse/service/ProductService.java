package com.techverse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techverse.model.Product;
import com.techverse.model.User;
import com.techverse.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

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
        	product.setProductImage1(storageService.uploadFileOnAzure(p1));
        	
        }
        
        
        if(p2 != null && !p2.isEmpty()) {
        	product.setProductImage2(storageService.uploadFileOnAzure(p2));
        	
        }
        
        if(p3 != null && !p3.isEmpty()) {
        	product.setProductImage3(storageService.uploadFileOnAzure(p3));
        	
        }
        if(p4 != null && !p4.isEmpty()) {
        	product.setProductImage4(storageService.uploadFileOnAzure(p4));
        	
        }
        if(p5 != null && !p5.isEmpty()) {
        	product.setProductImage1(storageService.uploadFileOnAzure(p5));
        	
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

        return productRepository.save(product);
    }
    
    
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public boolean changeProductStatus(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setActive(!product.isActive()); // Toggle status (active to inactive, inactive to active)
            productRepository.save(product);
            return true;
        }
        return false;
    }
}
