package com.ecommerce.vn.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTO {

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

    private List<OrderDetailDTO> orderDetails;
}
