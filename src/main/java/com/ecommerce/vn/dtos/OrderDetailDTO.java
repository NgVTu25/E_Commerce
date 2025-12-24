package com.ecommerce.vn.dtos;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderDetailDTO {
    private Integer productId;
    private Integer quantity;
    private Double discount;

    private BigDecimal unitPrice;
}
