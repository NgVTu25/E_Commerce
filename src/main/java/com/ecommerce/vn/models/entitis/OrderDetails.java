package com.ecommerce.vn.models.entitis;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "order_details")
public class OrderDetails {

    @EmbeddedId
    private OrderDetailsId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "OrderID")
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "ProductID")
    private Products product;

    @Column(name = "UnitPrice", precision = 8, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "Discount", nullable = false)
    private Double discount;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class OrderDetailsId implements Serializable {

        @Column(name = "OrderID")
        private Integer orderId;

        @Column(name = "ProductID")
        private Integer productId;
    }
}