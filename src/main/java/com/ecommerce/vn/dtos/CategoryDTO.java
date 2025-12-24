package com.ecommerce.vn.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDTO {

    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    private String categoryName;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    private byte[] picture;
}
