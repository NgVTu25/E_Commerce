package com.ecommerce.vn.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ShipperDTO {

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 200, message = "Company name must be between 2 and 200 characters")
    private String companyName;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s.]?[(]?[0-9]{1,4}[)]?[-\\s.]?[0-9]{1,9}$", message = "Invalid phone number format")
    private String phone;
}
