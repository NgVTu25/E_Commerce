package com.ecommerce.vn.models.entitis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal; // Important for Price

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private Integer id;

    @Column(name = "ProductName", length = 40, nullable = false)
    private String productName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SupplierID", nullable = false)
    @JsonIgnoreProperties({ "region", "postalCode", "contactTitle", "country", "contactName"})
    private Suppliers supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID", nullable = false)
    private Categories category;


    @Column(name = "QuantityPerUnit", length = 20)
    private String quantityPerUnit;

    @Column(name = "UnitPrice", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "UnitsInStock")
    private Integer unitsInStock;

    @Column(name = "UnitsOnOrder")
    private Integer unitsOnOrder;

    @Column(name = "ReorderLevel")
    private Integer reorderLevel;

    @Column(name = "Discontinued", nullable = false, columnDefinition = "int default 0")
    private Integer discontinued;
}