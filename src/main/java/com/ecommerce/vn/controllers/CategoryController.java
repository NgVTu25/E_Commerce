package com.ecommerce.vn.controllers;

import com.ecommerce.vn.dtos.CategoriesDTOs;
import com.ecommerce.vn.models.entitis.Categories;
import com.ecommerce.vn.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Categories>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categories> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<Categories> createCategory(@RequestBody CategoriesDTOs categoriesDTOs) {
        return ResponseEntity.ok(categoryService.createCategory(categoriesDTOs));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categories> updateCategory(@PathVariable Long id, @RequestBody CategoriesDTOs categoriesDTOs) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoriesDTOs));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Đã xóa danh mục thành công");
    }
}