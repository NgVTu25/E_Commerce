package com.ecommerce.vn.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDTO {

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 200, message = "Product name must be between 2 and 200 characters")
    private String productName;

    @Size(max = 100, message = "Quantity per unit must not exceed 100 characters")
    private String quantityPerUnit;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Unit price must have at most 10 integer digits and 2 decimal places")
    private BigDecimal unitPrice;

    @NotNull(message = "Units in stock is required")
    @PositiveOrZero(message = "Units in stock must be zero or positive")
    private Integer unitsInStock;

    @PositiveOrZero(message = "Units on order must be zero or positive")
    private Integer unitsOnOrder;

    @PositiveOrZero(message = "Reorder level must be zero or positive")
    private Integer reorderLevel;

    @Min(value = 0, message = "Discontinued must be 0 or 1")
    @Max(value = 1, message = "Discontinued must be 0 or 1")
    private Integer discontinued;

    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be positive")
    private Short categoryId;

    @NotNull(message = "Supplier ID is required")
    @Positive(message = "Supplier ID must be positive")
    private Integer supplierId;
}