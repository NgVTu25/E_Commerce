package com.ecommerce.vn.dtos;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CustomersDTOs {
    private String companyName;
    private String contactTitle;
    private String address;
    private String city;
    private String region;
    private String postalCode;
    private String phone;
    private String fax;
}
