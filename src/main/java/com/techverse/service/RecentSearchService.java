package com.techverse.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.model.Product;
import com.techverse.model.RecentSearch;
import com.techverse.model.User;
import com.techverse.repository.ProductRepository;
import com.techverse.repository.RecentSearchRepository;

@Service
public class RecentSearchService {

	@Autowired
	RecentSearchRepository recentSearchRepository;
	
	@Autowired
	ProductRepository productRepository;
	public List<RecentSearch> findRecentSearchesByUser(User user) {
	    // Assuming you have a repository for RecentSearch
	    return recentSearchRepository.findByUserOrderBySearchTimestampDesc(user);
	}
	public boolean hasRecentSearch(User user, Product product) {
        return recentSearchRepository.existsByUserAndProduct(user, product);
    }
	
	public void save(RecentSearch recentSearch) {
	    // Assuming you have a repository for RecentSearch
	    recentSearchRepository.save(recentSearch);
	}
	 public void delete(RecentSearch recentSearch) {
		 recentSearch.setProduct(null);
		 recentSearch.setUser(null);
	        recentSearchRepository.delete(recentSearch);
	    }
	 
	
	 
	 
}
