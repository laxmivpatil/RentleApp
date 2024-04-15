package com.techverse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techverse.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}