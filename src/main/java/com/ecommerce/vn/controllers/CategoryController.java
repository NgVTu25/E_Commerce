package com.ecommerce.vn.controllers;

import com.ecommerce.vn.dtos.CategoryDTO;
import com.ecommerce.vn.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Short id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<CategoryDTO>> getAllCategoriesByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getCategoryImage(@PathVariable Short id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        byte[] image = category.getPicture();

        if (image == null || image.length == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Short id, @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCategoryImage(@PathVariable Short id,
                                                      @RequestParam("file") MultipartFile file) {
        try {
            CategoryDTO category = categoryService.getCategoryById(id);
            category.setPicture(file.getBytes());
            categoryService.createCategory(category);
            return ResponseEntity.ok("Upload ảnh thành công!");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Lỗi khi upload ảnh");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Short id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}