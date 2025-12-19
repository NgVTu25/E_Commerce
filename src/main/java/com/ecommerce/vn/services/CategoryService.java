package com.ecommerce.vn.services;

import com.ecommerce.vn.dtos.CategoriesDTOs;
import com.ecommerce.vn.models.entitis.Categories;
import com.ecommerce.vn.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<Categories> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Categories> getCategoryByName(String name) {
        return categoryRepository.findByCategoryNameContaining(name);
    }

    public Categories getCategoryById(Short id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục có ID: " + id));
    }

    public Categories updateCategory(Short id, CategoriesDTOs categoryDetails) {
        Categories existingCategory = getCategoryById(id);
        modelMapper.map(categoryDetails, existingCategory);
        return categoryRepository.save(existingCategory);
    }

    public Categories createCategory(CategoriesDTOs categoriesDTOs) {
        Categories category = new Categories();
        modelMapper.map(categoriesDTOs, category);
        return categoryRepository.save(category);
    }


    public void deleteCategory(Short id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy danh mục để xóa");
        }
        categoryRepository.deleteById(id);
    }
}