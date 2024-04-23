package com.techverse.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
