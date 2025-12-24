package com.ecommerce.vn.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 200, message = "Company name must be between 2 and 200 characters")
    private String companyName;

    @Size(max = 100, message = "Contact title must not exceed 100 characters")
    private String contactTitle;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @Size(max = 100, message = "Region must not exceed 100 characters")
    private String region;

    @Pattern(regexp = "^[0-9]{5,10}$", message = "Postal code must be 5-10 digits")
    private String postalCode;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s.]?[(]?[0-9]{1,4}[)]?[-\\s.]?[0-9]{1,9}$", message = "Invalid phone number format")
    private String phone;

    @Pattern(regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s.]?[(]?[0-9]{1,4}[)]?[-\\s.]?[0-9]{1,9}$", message = "Invalid fax number format")
    private String fax;
}
