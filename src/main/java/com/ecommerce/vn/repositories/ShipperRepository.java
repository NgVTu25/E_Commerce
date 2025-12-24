package com.ecommerce.vn.repositories;

import com.ecommerce.vn.dtos.ShipperDTO;
import com.ecommerce.vn.models.entitis.Shippers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipperRepository extends JpaRepository<Shippers, Long> {
    List<ShipperDTO> getShippersById(Long id);
}
