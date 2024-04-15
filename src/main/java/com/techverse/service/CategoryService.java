package com.techverse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techverse.model.Category;
import com.techverse.model.Subcategory;
import com.techverse.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    
    
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category addCategory(Category category) {
    	 List<Subcategory> subcategories = category.getSubcategories();
         for (Subcategory subcategory : subcategories) {
             subcategory.setCategory(category);
         }
         category.setSubcategories(subcategories);
         return categoryRepository.save(category);
    }
    
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

}
