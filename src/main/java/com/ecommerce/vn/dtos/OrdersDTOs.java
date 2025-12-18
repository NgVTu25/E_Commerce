package com.ecommerce.vn.dtos;

import com.ecommerce.vn.models.entitis.Customers;
import com.ecommerce.vn.models.entitis.Employees;
import com.ecommerce.vn.models.entitis.Shippers;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OrdersDTOs {

    private LocalDate orderDate;
    private LocalDate requiredDate;
    private LocalDate shippedDate;
    private BigDecimal freight;
    private String shipName;
    private String shipAddress;
    private String shipCity;
    private String shipRegion;
    private String shipPostalCode;
    private String shipCountry;

    private Integer employeeId;
    private Long shipVia;
    private String customerId;
}
