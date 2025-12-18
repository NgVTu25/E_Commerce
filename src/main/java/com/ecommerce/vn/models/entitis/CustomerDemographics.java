package com.ecommerce.vn.models.entitis;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "customerDemographics")
public class CustomerDemographics {

    @Id
    @Column(name = "CustomerTypeID", length = 10)
    private String id;

    @Column(name = "CustomerDesc", columnDefinition = "TEXT")
    private String customerDesc;
}