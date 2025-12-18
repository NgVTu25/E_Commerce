package com.ecommerce.vn.models.entitis;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Territories")
public class Territories {

    @Id
    @Column(name = "TerritoryID", length = 20)
    private String id;

    @Column(name = "TerritoryDescription", length = 50, nullable = false)
    private String territoryDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RegionID", nullable = false)
    private Region region;
}