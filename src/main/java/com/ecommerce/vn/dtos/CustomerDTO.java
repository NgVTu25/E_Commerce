package com.ecommerce.vn.dtos;

import lombok.Data;

@Data
public class CustomerDTO {
    private String companyName;
    private String contactTitle;
    private String address;
    private String city;
    private String region;
    private String postalCode;
    private String phone;
    private String fax;
}
