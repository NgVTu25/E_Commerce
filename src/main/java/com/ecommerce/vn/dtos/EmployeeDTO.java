package com.ecommerce.vn.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EmployeeDTO {
    private Integer employeeId;
    private String lastName;
    private String firstName;
    private String title;
    private String titleOfCourtesy;
    private LocalDateTime birthDate;
    private LocalDateTime hireDate;
    private String address;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String homePhone;
    private String extension;
    private Integer reportsTo;
    private List<String> territoryIds;
}
