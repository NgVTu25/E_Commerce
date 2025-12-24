package com.ecommerce.vn.repositories;

import com.ecommerce.vn.models.entitis.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Suppliers, Integer> {
}
