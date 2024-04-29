package com.techverse.model;

 

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class RecentSearch {
   
    
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne(cascade = CascadeType.REMOVE) // Cascade deletion if associated product is deleted
	    private Product product;

	    private LocalDateTime searchTimestamp;

	    @JsonIgnore
	    @ManyToOne
	    private User user;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}

		  

		public LocalDateTime getSearchTimestamp() {
			return searchTimestamp;
		}

		public void setSearchTimestamp(LocalDateTime searchTimestamp) {
			this.searchTimestamp = searchTimestamp;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
    
	    
	    

    // Getters and setters...
}




