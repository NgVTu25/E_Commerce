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
@Table(name = "region")
public class Region {
    @Id
    @Column(name = "RegionID")
    private Long id;

    @Column(name = "RegionDescription", length = 50)
    private String regionDescription;
}
