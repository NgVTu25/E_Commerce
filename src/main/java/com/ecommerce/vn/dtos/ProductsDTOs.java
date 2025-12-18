package com.ecommerce.vn.dtos;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductsDTOs {

    private String productName;
    private String quantityPerUnit;
    private BigDecimal unitPrice;
    private Integer unitsInStock;
    private Integer unitsOnOrder;
    private Integer reorderLevel;
    private Boolean discontinued;

    private Long categoryId;
    private Long supplierId;
}