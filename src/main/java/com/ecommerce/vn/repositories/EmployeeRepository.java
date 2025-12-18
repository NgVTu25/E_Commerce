package com.ecommerce.vn.repositories;

import com.ecommerce.vn.models.entitis.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Integer> {

}
