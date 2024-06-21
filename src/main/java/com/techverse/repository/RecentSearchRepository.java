package com.techverse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techverse.model.Product;
import com.techverse.model.RecentSearch;
import com.techverse.model.User;

@Repository
public interface RecentSearchRepository extends JpaRepository<RecentSearch, Long> {
	 List<RecentSearch> findByUserOrderBySearchTimestampDesc(User user);
	 boolean existsByUserAndProduct(User user, Product product);
	 Optional<RecentSearch> findByUserAndProduct(User user, Product product);
}
