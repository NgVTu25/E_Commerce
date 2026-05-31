package com.ecommerce.vn.repositories;

import com.ecommerce.vn.models.entitis.EmployeeTerritories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeTerritoriesRepository
        extends JpaRepository<EmployeeTerritories, EmployeeTerritories.EmployeeTerritoriesId> {

    @Query("SELECT et FROM EmployeeTerritories et JOIN FETCH et.territory WHERE et.employee.id = :employeeId")
    List<EmployeeTerritories> findByEmployeeIdWithTerritory(Integer employeeId);

    @Query("SELECT et FROM EmployeeTerritories et JOIN FETCH et.employee WHERE et.territory.id = :territoryId")
    List<EmployeeTerritories> findByTerritoryIdWithEmployee(String territoryId);
}
