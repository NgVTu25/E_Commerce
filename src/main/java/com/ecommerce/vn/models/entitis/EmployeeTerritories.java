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
@Table(name = "employeeTerritories")
public class EmployeeTerritories {

    @EmbeddedId
    private EmployeeTerritoriesId id;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "EmployeeID")
    private Employees employee;

    @ManyToOne
    @MapsId("territoryId")
    @JoinColumn(name = "TerritoryID")
    private Territories territory;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class EmployeeTerritoriesId implements Serializable {

        @Column(name = "EmployeeID")
        private Integer employeeId; // Changed from Long to Integer to match Employees entity

        @Column(name = "TerritoryID")
        private String territoryId;
    }
}