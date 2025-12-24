package com.ecommerce.vn.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderDetailDTO {

    @NotNull(message = "Product ID is required")
    @Positive(message = "Product ID must be positive")
    private Integer productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Discount is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Discount must be zero or positive")
    @DecimalMax(value = "1.0", inclusive = true, message = "Discount must not exceed 1.0 (100%)")
    private Double discount;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Unit price must have at most 10 integer digits and 2 decimal places")
    private BigDecimal unitPrice;
}
