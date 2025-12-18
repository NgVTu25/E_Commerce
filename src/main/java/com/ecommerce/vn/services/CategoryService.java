package com.ecommerce.vn.services;

import com.ecommerce.vn.dtos.CategoriesDTOs;
import com.ecommerce.vn.models.entitis.Categories;
import com.ecommerce.vn.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Categories> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Categories> getCategoryByName(String name) {
        return categoryRepository.findByCategoryNameContaining(name);
    }

    public Categories getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục có ID: " + id));
    }

    public Categories updateCategory(Long id, CategoriesDTOs categoryDetails) {
        Categories existingCategory = getCategoryById(id);
        existingCategory.setCategoryName(categoryDetails.getCategoryName());
        existingCategory.setDescription(categoryDetails.getDescription());

        return categoryRepository.save(existingCategory);
    }

    public Categories createCategory(CategoriesDTOs categoriesDTOs) {
        Categories category = Categories.builder()
                .categoryName(categoriesDTOs.getCategoryName())
                .description(categoriesDTOs.getDescription())
                .picture(categoriesDTOs.getPicture())
                .build();

        return categoryRepository.save(category);
    }


    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy danh mục để xóa");
        }
        categoryRepository.deleteById(id);
    }
}