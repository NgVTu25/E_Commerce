package com.ecommerce.vn.models.entitis;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "customercustomerdemo")
public class CustomerCustomerDemo {

    @EmbeddedId
    private CustomerCustomerDemoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("customerId")
    @JoinColumn(name = "CustomerID")
    private Customers customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("customerTypeId")
    @JoinColumn(name = "CustomerTypeID")
    private CustomerDemographics customerDemographics;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class CustomerCustomerDemoId implements Serializable {

        @Column(name = "CustomerID")
        private String customerId;

        @Column(name = "CustomerTypeID")
        private String customerTypeId;
    }
}