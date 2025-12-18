package com.ecommerce.vn.repositories;

import com.ecommerce.vn.models.entitis.Shippers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipperRepository extends JpaRepository<Shippers, Long> {
}
