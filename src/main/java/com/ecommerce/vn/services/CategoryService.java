package com.ecommerce.vn.services;

import com.ecommerce.vn.dtos.CategoryDTO;
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

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, CategoryDTO.class))
                .toList();
    }

    public List<CategoryDTO> getCategoryByName(String name) {
        return categoryRepository.findByCategoryNameContaining(name).stream().map(c -> modelMapper.map(c, CategoryDTO.class)).toList();
    }

    public CategoryDTO getCategoryById(Short id) {
        Categories category = categoryRepository.findById(id).orElseThrow(()  -> new RuntimeException("Category not found"));
        return modelMapper.map(category, CategoryDTO.class);
    }

    public CategoryDTO updateCategory(Short id, CategoryDTO categoryDetails) {
        Categories existingCategory = categoryRepository.findById(id).orElseThrow
                (()  -> new RuntimeException("Category not found"));
        modelMapper.map(categoryDetails, existingCategory);
        return categoryDetails;
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Categories category = modelMapper.map(categoryDTO, Categories.class);
        categoryRepository.save(category);
        return categoryDTO;
    }

    public void deleteCategory(Short id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy danh mục để xóa");
        }
        categoryRepository.deleteById(id);
    }
}