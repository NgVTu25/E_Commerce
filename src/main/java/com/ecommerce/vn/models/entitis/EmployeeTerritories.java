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
@Table(name = "EmployeeTerritories")
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

        private Long employeeId;

        private String territoryId;
    }
}