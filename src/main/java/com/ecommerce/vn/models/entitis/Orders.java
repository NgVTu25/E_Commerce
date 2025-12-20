package com.ecommerce.vn.models.entitis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID")
    private Customers customer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EmployeeID", nullable = false)
    private Employees employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ShipVia")
    private Shippers shipper;

    @Column(name = "OrderDate")
    private LocalDate orderDate;

    @Column(name = "RequiredDate")
    private LocalDate requiredDate;

    @Column(name = "ShippedDate")
    private LocalDate shippedDate;

    @Column(name = "Freight", precision = 10, scale = 2)
    private BigDecimal freight;

    @Column(name = "ShipName", length = 40)
    private String shipName;

    @Column(name = "ShipAddress", length = 60)
    private String shipAddress;

    @Column(name = "ShipCity", length = 15)
    private String shipCity;

    @Column(name = "ShipRegion", length = 15)
    private String shipRegion;

    @Column(name = "ShipPostalCode", length = 10)
    private String shipPostalCode;

    @Column(name = "ShipCountry", length = 15)
    private String shipCountry;
}