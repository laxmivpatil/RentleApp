package com.techverse.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.google.api.services.sqladmin.model.User;
import com.techverse.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	//List<Product> findByPrice_ValueGreaterThan(BigDecimal price);

  //  List<Product> findByUser(User user);
    // Other custom query methods
	  List<Product> findByUserId(Long userId);
	  
	  
	  List<Product> findAllByUserIdNotAndActiveTrue(Long userId);
	  
	  
	  List<Product> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String title, String category);
	  
	  @Query(value = "SELECT p FROM Product p ORDER BY p.averageRating DESC")
	    List<Product> findTop15PopularProducts();
	  
	  
	  List<Product> findTop5ByActiveOrderByCreatedAtDesc(boolean active);
	  
	  
	  List<Product> findByCategoryIn(Set<String> categories);
	  
	  
	  List<Product> findAllByUserIdNotAndActiveTrueAndCategory(Long userId,String category);
}
