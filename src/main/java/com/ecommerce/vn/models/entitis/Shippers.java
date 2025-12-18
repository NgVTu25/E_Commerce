package com.ecommerce.vn.models.entitis;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "shippers")
public class Shippers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShipperID")
    private Long id;

    @Column(name = "CompanyName", length = 40)
    private String companyName;

    @Column(name = "Phone", length = 24)
    private String phone;

}
