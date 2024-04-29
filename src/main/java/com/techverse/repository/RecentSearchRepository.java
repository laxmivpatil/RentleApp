package com.techverse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techverse.model.RecentSearch;
import com.techverse.model.User;

@Repository
public interface RecentSearchRepository extends JpaRepository<RecentSearch, Long> {
	 List<RecentSearch> findByUserOrderBySearchTimestampDesc(User user);

}
