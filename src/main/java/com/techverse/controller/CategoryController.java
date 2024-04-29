package com.techverse.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techverse.model.Category;
import com.techverse.model.Subcategory;
import com.techverse.repository.CategoryRepository;
import com.techverse.service.CategoryService;
import com.techverse.service.StorageService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private CategoryRepository categoryrepository;
    
    @Autowired
    private StorageService storageService;

    @PostMapping
    public Category addCategoryWithSubcategories(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }
    @GetMapping
    public Map<String, Object> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();

        Map<String, Object> response = new HashMap<>();
        response.put("categories", categories.stream()
                .map(category -> {
                    Map<String, Object> categoryMap = new HashMap<>();
                    categoryMap.put("id", category.getId());
                    categoryMap.put("name", category.getName());
                    categoryMap.put("image", category.getImage());
                    return categoryMap;
                })
                .collect(Collectors.toList()));

        return response;
    }
    
    @PutMapping("/update-image/{categoryId}")
    public ResponseEntity<String> updateCategoryImage(
            @PathVariable Long categoryId, 
            @RequestParam("image") MultipartFile image) {
       Category updatedCategory = categoryrepository.findById(categoryId).get();
       categoryrepository.save(updatedCategory);
        String path=storageService.uploadFileOnAzure(image);
        updatedCategory.setImage(path); 
        return ResponseEntity.ok(path);
    }
    
    @GetMapping("/all")
    public Map<String, List<Category>> getAllCategorieswithsub() {
        List<Category> categories = categoryService.getAllCategories();
        Map<String, List<Category>> response = new HashMap<>();
        response.put("categories", categories);
        return response;
    }
    
    
    
    @GetMapping("/sub/{categoryId}")
    public Map<String, Object> getSubcategoriesByCategoryId(@PathVariable Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        if (category == null) {
            // Handle category not found error
            // You can throw an exception or return an appropriate response
            // Here, I'm returning an empty response for simplicity
            return new HashMap<>();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("categoryId", category.getId());
        response.put("categoryName", category.getName());
        response.put("subcategories", category.getSubcategories().stream()
                .map(subcategory -> {
                    Map<String, Object> subcategoryMap = new HashMap<>();
                    subcategoryMap.put("id", subcategory.getId());
                    subcategoryMap.put("name", subcategory.getName());
                    return subcategoryMap;
                })
                .collect(Collectors.toList()));

        return response;
    }

}
