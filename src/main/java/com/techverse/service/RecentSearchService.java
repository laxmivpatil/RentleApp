package com.techverse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.model.RecentSearch;
import com.techverse.model.User;
import com.techverse.repository.RecentSearchRepository;

@Service
public class RecentSearchService {

	@Autowired
	RecentSearchRepository recentSearchRepository;
	
	public List<RecentSearch> findRecentSearchesByUser(User user) {
	    // Assuming you have a repository for RecentSearch
	    return recentSearchRepository.findByUserOrderBySearchTimestampDesc(user);
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
