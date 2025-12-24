package com.ecommerce.vn.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTO {

    @NotNull(message = "Order date is required")
    @PastOrPresent(message = "Order date must be in the past or present")
    private LocalDate orderDate;

    @NotNull(message = "Required date is required")
    @Future(message = "Required date must be in the future")
    private LocalDate requiredDate;

    @PastOrPresent(message = "Shipped date must be in the past or present")
    private LocalDate shippedDate;

    @NotNull(message = "Freight is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Freight must be zero or positive")
    @Digits(integer = 10, fraction = 2, message = "Freight must have at most 10 integer digits and 2 decimal places")
    private BigDecimal freight;

    @NotBlank(message = "Ship name is required")
    @Size(max = 200, message = "Ship name must not exceed 200 characters")
    private String shipName;

    @NotBlank(message = "Ship address is required")
    @Size(max = 255, message = "Ship address must not exceed 255 characters")
    private String shipAddress;

    @NotBlank(message = "Ship city is required")
    @Size(max = 100, message = "Ship city must not exceed 100 characters")
    private String shipCity;

    @Size(max = 100, message = "Ship region must not exceed 100 characters")
    private String shipRegion;

    @Pattern(regexp = "^[0-9]{5,10}$", message = "Ship postal code must be 5-10 digits")
    private String shipPostalCode;

    @NotBlank(message = "Ship country is required")
    @Size(max = 100, message = "Ship country must not exceed 100 characters")
    private String shipCountry;

    @NotNull(message = "Employee ID is required")
    @Positive(message = "Employee ID must be positive")
    private Integer employeeId;

    @NotNull(message = "Ship via is required")
    @Positive(message = "Ship via must be positive")
    private Long shipVia;

    @NotBlank(message = "Customer ID is required")
    @Size(max = 50, message = "Customer ID must not exceed 50 characters")
    private String customerId;

    @NotNull(message = "Order details are required")
    @NotEmpty(message = "Order must have at least one detail")
    @Valid
    private List<OrderDetailDTO> orderDetails;
}
