package com.techverse.repository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techverse.model.Product;
import com.techverse.model.Rating;
import com.techverse.model.User;

public interface RatingRepository extends JpaRepository<Rating, Long> {

	@Query("SELECT r From Rating r Where r.product.id=:productId")
	public List<Rating> getAllProductsRatings(@Param("productId")Long productId);
	
	
	@Query("SELECT AVG(r.rating) FROM Rating r WHERE r.product.id = :productId")
    Double getAverageRatingForProduct(@Param("productId") Long productId);
	
	
	 @Query(value = "SELECT rating, COUNT(*) FROM Rating WHERE product_id = :productId GROUP BY rating", nativeQuery = true)
	    List<Object[]> countRatingsGroupedByValue(@Param("productId") Long productId);

	    Optional<Rating> findByUserAndProduct(User user, Product product);
}
