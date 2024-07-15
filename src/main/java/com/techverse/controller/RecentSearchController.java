package com.techverse.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techverse.exception.UserException;
import com.techverse.model.Product;
import com.techverse.model.RecentSearch;
import com.techverse.model.User;
import com.techverse.service.RecentSearchService;
import com.techverse.service.UserService;

@RestController
public class RecentSearchController {
    @Autowired
    private RecentSearchService recentSearchService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/api/recentsearch")
    public  Map<String, Object> getRecentSearchesByUser(@RequestHeader("Authorization") String authorizationHeader)   {
    	Map<String,Object> response = new HashMap<>();
        
    	try {
    	User user=userService.findUserProfileByJwt(authorizationHeader).get();
        // Call the service method to fetch recent searches by user ID
        List<RecentSearch> recentSearches = recentSearchService.findRecentSearchesByUser(user);
        List<Product> products=new ArrayList<>();
        for (RecentSearch recentSearch : recentSearches) {
            products.add(recentSearch.getProduct());
        }
        // Return the response with recent searches and associated products
         response.put("product",products);

        response.put("status", true);
        response.put("message", "recent product retrived Successfully");
        return response;
    	}
    	catch(UserException e) {
    		response.put("status", true);
            response.put("message", "token error");
            return response;
    	}
    	catch(Exception e) {
    		response.put("status", true);
            response.put("message", "internal server error");
            return response;
    	}
    	
    }
    
   
}